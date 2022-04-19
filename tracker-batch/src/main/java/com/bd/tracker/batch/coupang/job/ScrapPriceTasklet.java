package com.bd.tracker.batch.coupang.job;

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
        List<BatchInfoResponse> batchInfoList = webClient.get()
                .uri("/batchInfo")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BatchInfoResponse>>() {})
                .block();

        if (batchInfoList == null || batchInfoList.size() == 0) {
            log.info("스크랩할 정보가 없습니다.");
            return RepeatStatus.FINISHED;
        }

        List<ScrapInfoDto> priceInfoList = new ArrayList<>();
        Document doc = null;

        for (BatchInfoResponse dto : batchInfoList) {
            try {
                doc = Jsoup.connect(dto.getUrl()).timeout(5000).get();
            } catch (IOException e) {
                log.info("타겟 URL : {} , 접속 중 에러가 발생하였습니다. {}", dto.getUrl(), e.getMessage());
            }
            // TODO : 접속 실패시 처리 필요
            // TODO : 첫 번째는 정상가, 만약 할인가가 있을 시 밑으로 가격이 더 있으니 해당 처리 필요
            // TODO : Elements 로 읽어 Size 로 확인하자.
            Element priceElement = doc.select(dto.getCssQuery()).first();
            if (priceElement == null) {
                log.info("URL : {}, CssQuery : {}. css 가 존재하지 않습니다.", dto.getUrl(), dto.getCssQuery());
                continue;
            }

            String price = priceElement.text();
            if (!price.contains("원")) {
                log.info("URL : {}, CssQuery : {}. 가격 정보가 존재하지 않습니다.", dto.getUrl(), dto.getCssQuery());
                continue;
            }

            priceInfoList.add(ScrapInfoDto.of(dto.getId(), Integer.parseInt(price.replaceAll("[^0-9]", ""))));
        }

        ScrapInfoRequest request = new ScrapInfoRequest(priceInfoList);

        Boolean success = webClient.post()
                .uri("/scrapInfo")
                .body(Mono.just(request), ScrapInfoRequest.class)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        log.info("스크래핑 결과 전송 완료. {}", success);

        return RepeatStatus.FINISHED;
    }

}
