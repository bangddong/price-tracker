package com.bd.tracker.api.controller;

import com.bd.tracker.api.dto.ApiDataResponse;
import com.bd.tracker.api.service.ScrapInfoService;
import com.bd.tracker.core.dto.ScrapInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class ApiScrapInfoController {

    private final ScrapInfoService scrapInfoService;

    @PostMapping("/scrapInfo")
    public ApiDataResponse<String> createScrapInfo(@RequestBody ScrapInfoRequest scrapInfoRequest) {
        // TODO : forEach 저장과 일괄 저장의 성능상 차이는 ?
        scrapInfoRequest.getPriceInfoList().forEach(scrapInfoService::createScrapInfo);
        return ApiDataResponse.of(Boolean.toString(true));
    }


}
