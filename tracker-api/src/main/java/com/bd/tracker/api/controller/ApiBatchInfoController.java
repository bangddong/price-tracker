package com.bd.tracker.api.controller;

import com.bd.tracker.api.service.BatchInfoService;
import com.bd.tracker.core.dto.BatchInfoResponse;
import com.bd.tracker.core.dto.ScrapInfoRequest;
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

    @GetMapping("/scrapInfo")
    public List<BatchInfoResponse> getScrapInfo() {
        return batchInfoService.getScrapInfo()
                .stream()
                .map(BatchInfoResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/scrapInfo")
    public Boolean createScrapInfo(@RequestBody ScrapInfoRequest scrapInfoRequest) {
        // TODO : forEach 저장과 일괄 저장의 성능상 차이는 ?
        scrapInfoRequest.getPriceInfoList().forEach(batchInfoService::createScrapInfo);
        return true;
    }

    // TODO : 신규 스크래핑 추가 PostMapping 필요

}
