package com.bd.tracker.api.controller;

import com.bd.tracker.api.dto.ApiDataResponse;
import com.bd.tracker.api.service.BatchInfoService;
import com.bd.tracker.core.constant.ScrapCategory;
import com.bd.tracker.core.dto.BatchInfoRequest;
import com.bd.tracker.core.dto.BatchInfoResponse;
import com.bd.tracker.core.dto.BatchInfoResponseDto;
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

    @GetMapping("/batchInfo/{category}")
    public ApiDataResponse<BatchInfoResponse> getBatchInfo(@PathVariable ScrapCategory category) {
        return ApiDataResponse.of(
                BatchInfoResponse.of(
                        batchInfoService.getBatchInfo(category)
                        .stream()
                        .map(BatchInfoResponseDto::from)
                        .collect(Collectors.toList())
                )
        );
    }

    @PostMapping("/batchInfo")
    public ApiDataResponse<String> createBatchInfo(@Valid @RequestBody BatchInfoRequest batchInfoRequest) {
        boolean result = batchInfoService.createBatchInfo(batchInfoRequest);
        return ApiDataResponse.of(Boolean.toString(result));
    }

}
