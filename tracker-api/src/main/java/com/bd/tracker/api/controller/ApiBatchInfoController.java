package com.bd.tracker.api.controller;

import com.bd.tracker.api.dto.ApiDataResponse;
import com.bd.tracker.api.service.BatchInfoService;
import com.bd.tracker.core.dto.BatchInfoRequest;
import com.bd.tracker.core.dto.BatchInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class ApiBatchInfoController {

    private final BatchInfoService batchInfoService;

    @GetMapping("/batchInfo")
    public ApiDataResponse<List<BatchInfoResponse>> getBatchInfo() {
        return ApiDataResponse.of(batchInfoService.getBatchInfo()
                .stream()
                .map(BatchInfoResponse::from)
                .collect(Collectors.toList()));
    }

    @PostMapping("/batchInfo")
    public ApiDataResponse<Void> createBatchInfo(@Valid BatchInfoRequest batchInfoRequest) {
        batchInfoService.createBatchInfo(batchInfoRequest);
        return ApiDataResponse.empty();
    }

}
