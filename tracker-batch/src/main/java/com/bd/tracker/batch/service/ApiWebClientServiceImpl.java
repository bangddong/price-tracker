package com.bd.tracker.batch.service;

import com.bd.tracker.batch.coupang.dto.ApiData;
import com.bd.tracker.core.dto.BatchInfoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ApiWebClientServiceImpl implements ApiWebClientService{

    private final WebClient webClient;

    public ApiWebClientServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api").build();
    }

    @Override
    public List<BatchInfoResponse> getBatchInfo() {
        ApiData<List<BatchInfoResponse>> batchInfoResponse = this.webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/batchInfo/COUPANG_PRICE")
                                .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiData<List<BatchInfoResponse>>>(){})
                .block();

        return batchInfoResponse.getData();
    }

    @Override
    public void sendScrapResult() {

    }
}
