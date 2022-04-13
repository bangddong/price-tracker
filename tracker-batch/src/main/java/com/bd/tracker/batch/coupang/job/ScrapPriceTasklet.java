package com.bd.tracker.batch.coupang.job;

import com.bd.tracker.core.dto.BatchInfoDto;
import com.bd.tracker.core.dto.PriceInfoDto;
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

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ScrapPriceTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        WebClient webClient = WebClient.create("http://localhost:8080/api/scrapInfo");
        List<BatchInfoDto> batchInfoList = webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BatchInfoDto>>() {})
                .block();

        if (batchInfoList == null || batchInfoList.size() == 0) {
            log.info("스크랩할 정보가 없습니다.");
            return RepeatStatus.FINISHED;
        }

        List<PriceInfoDto> priceInfoList = new ArrayList<>();

        for (BatchInfoDto dto : batchInfoList) {
            Document doc = Jsoup.connect(dto.getUrl()).get();
            Element priceElement = doc.select(dto.getCssQuery()).last();
            if (priceElement == null) {
                log.info("{}에서 {} 선택자를 찾을 수 없습니다.", dto.getUrl(), dto.getCssQuery());
                continue;
            }
            String price = priceElement.text();

            //TODO priceInfoList 에 정보모아 API 서버에 전달
        }

        return RepeatStatus.FINISHED;
    }

}
