package com.bd.tracker.api.controller;

import com.bd.tracker.api.service.BatchInfoService;
import com.bd.tracker.core.dto.BatchInfoRequest;
import com.bd.tracker.core.dto.BatchInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class ApiBatchInfoController {

    private final BatchInfoService batchInfoService;

    @GetMapping("/batchInfo")
    public List<BatchInfoResponse> getBatchInfo() {
        return batchInfoService.getBatchInfo()
                .stream()
                .map(BatchInfoResponse::from)
                .collect(Collectors.toList());
    }

    // TODO : 신규 스크래핑 추가 PostMapping 필요
    @PostMapping("/batchInfo")
    public void createBatchInfo(@RequestBody BatchInfoRequest batchInfoRequest) {
        batchInfoService.createBatchInfo(batchInfoRequest);
    }

}
