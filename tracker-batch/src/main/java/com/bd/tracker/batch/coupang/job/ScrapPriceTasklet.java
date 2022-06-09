package com.bd.tracker.batch.coupang.job;

import com.bd.tracker.core.dto.BatchInfoResponse;
import com.bd.tracker.core.dto.BatchInfoResponseDto;
import com.bd.tracker.core.dto.ScrapInfoDto;
import com.bd.tracker.core.dto.ScrapInfoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ScrapPriceTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                // TODO : 로그 필요할 때 사용
//                .filter(ExchangeFilterFunction.ofResponseProcessor(
//                        clientResponse -> clientResponse.bodyToMono(String.class)
//                                .flatMap(body -> {
//                                    log.error("Body is {}", body);
//                                    return Mono.just(clientResponse);
//                                })
//                ))
                .build();

        BatchInfoResponse batchInfoList = webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.path("/batchInfo/COUPANG_PRICE")
                            .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BatchInfoResponse>() {})
                .block();

        if (batchInfoList == null || batchInfoList.getResponseDtoList().size() == 0) {
            log.info("스크랩할 정보가 없습니다.");
            return RepeatStatus.FINISHED;
        }

        List<ScrapInfoDto> priceInfoList = new ArrayList<>();
        Document doc = null;

        for (BatchInfoResponseDto dto : batchInfoList.getResponseDtoList()) {
            try {
                doc = Jsoup.connect(dto.getUrl()).timeout(5000).get();
            } catch (IOException e) {
                log.info("타겟 URL : {}, 접속 중 에러가 발생하였습니다. {}", dto.getUrl(), e.getMessage());
            }
            if (doc == null) {
                log.info("타겟 URL : {}, HTML 문서를 읽지 못했습니다.", dto.getUrl());
                continue;
            }

            Element priceElement = doc.select(dto.getCssQuery()).last();
            if (priceElement == null) {
                log.info("URL : {}, CssQuery : {}. css 가 존재하지 않습니다.", dto.getUrl(), dto.getCssQuery());
                continue;
            } else if (priceElement.text().equals("원")) { // 해당 element 는 있으나 가격이 비어있는 경우가 있음
                priceElement = doc.select(dto.getCssQuery()).first();
            }

            String price = priceElement.text();
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
