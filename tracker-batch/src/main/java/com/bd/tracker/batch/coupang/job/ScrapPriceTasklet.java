package com.bd.tracker.batch.coupang.job;

import com.bd.tracker.batch.service.ApiWebClientService;
import com.bd.tracker.core.dto.BatchInfoResponse;
import com.bd.tracker.core.dto.ScrapInfoDto;
import com.bd.tracker.core.dto.ScrapInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.asynchttpclient.Dsl.asyncHttpClient;

@Log4j2
@RequiredArgsConstructor
public class ScrapPriceTasklet implements Tasklet {

    private final ApiWebClientService apiWebClientService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // TODO : WebClient 통해 통신하는 부분을 분리하여 순수 크롤링 소스만 남겨보자
//        WebClient webClient = WebClient.create("http://localhost:8080/api");
//
//        ApiData<List<BatchInfoResponse>> batchInfoResponse = webClient.get()
//                .uri(uriBuilder ->
//                    uriBuilder.path("/batchInfo/COUPANG_PRICE")
//                            .build()
//                )
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<ApiData<List<BatchInfoResponse>>>(){})
//                .block();

        List<BatchInfoResponse> batchInfoList = apiWebClientService.getBatchInfo();

        if (batchInfoList == null || batchInfoList.size() == 0) {
            log.info("스크랩할 정보가 없습니다.");
            return RepeatStatus.FINISHED;
        }

        List<ScrapInfoDto> priceInfoList = new ArrayList<>();
        Document doc = null;

        for (BatchInfoResponse dto : batchInfoList) {
            try (AsyncHttpClient client = asyncHttpClient()){
                Future<Response> f = client.prepareGet(dto.getUrl()).addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36").execute();
                Response resp = f.get(10, TimeUnit.SECONDS);
                System.out.println(resp);

                doc = Jsoup.connect(dto.getUrl()).timeout(10 * 1000).get();
            } catch (IOException e) {
                log.info("접속 중 에러가 발생하였습니다.\n타겟 URL : {},\n{}", dto.getUrl(), e.getMessage());
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

//        ApiData<String> scrapInfoResponse = webClient.post()
//                .uri("/scrapInfo")
//                .body(Mono.just(request), ScrapInfoRequest.class)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<ApiData<String>>(){})
//                .block();
//
//        log.info("스크래핑 결과 전송 완료. {}", scrapInfoResponse.getData());

        return RepeatStatus.FINISHED;
    }

}
