package com.bd.tracker.batch.coupang.job;

import com.bd.tracker.batch.coupang.dto.ApiData;
import com.bd.tracker.core.dto.BatchInfoResponse;
import com.bd.tracker.core.dto.ScrapInfoDto;
import com.bd.tracker.core.dto.ScrapInfoRequest;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ScrapPriceTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        WebClient webClient = WebClient.create("http://localhost:8080/api");

        ApiData<List<BatchInfoResponse>> batchInfoResponse = webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.path("/batchInfo/COUPANG_PRICE")
                            .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiData<List<BatchInfoResponse>>>(){})
                .block();

        List<BatchInfoResponse> batchInfoList = batchInfoResponse.getData();

        if (batchInfoList == null || batchInfoList.size() == 0) {
            log.info("스크랩할 정보가 없습니다.");
            return RepeatStatus.FINISHED;
        }

        List<ScrapInfoDto> priceInfoList = new ArrayList<>();
        Document doc = null;

        for (BatchInfoResponse dto : batchInfoList) {
            try {
                doc = Jsoup.connect(dto.getUrl()).timeout(10000).get();
            } catch (IOException e) {
                log.info("접속 중 에러가 발생하였습니다.\n타겟 URL : {},\n {}", dto.getUrl(), e.getMessage());
            }
            if (doc == null) {
                log.info("HTML 문서를 읽지 못했습니다.\n타겟 URL : {}\n", dto.getUrl());
                continue;
            }

            Element priceElement = doc.select(dto.getCssQuery()).last();
            if (priceElement == null) {
                log.info("css 가 존재하지 않습니다.\nURL : {}, \nCssQuery : {}.", dto.getUrl(), dto.getCssQuery());
                continue;
            } else if (priceElement.text().equals("원")) { // 해당 element 는 있으나 가격이 비어있는 경우가 있음
                priceElement = doc.select(dto.getCssQuery()).first();
            }

            String price = priceElement.text();
            priceInfoList.add(ScrapInfoDto.of(dto.getId(), Integer.parseInt(price.replaceAll("[^0-9]", ""))));
        }

        ScrapInfoRequest request = new ScrapInfoRequest(priceInfoList);

        ApiData<String> scrapInfoResponse = webClient.post()
                .uri("/scrapInfo")
                .body(Mono.just(request), ScrapInfoRequest.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiData<String>>(){})
                .block();

        log.info("스크래핑 결과 전송 완료. {}", scrapInfoResponse.getData());

        return RepeatStatus.FINISHED;
    }

}
