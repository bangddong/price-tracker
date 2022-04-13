package com.bd.tracker.api.controller;

import com.bd.tracker.api.service.BatchInfoService;
import com.bd.tracker.core.dto.BatchInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiBatchInfoController {

    private final BatchInfoService batchInfoService;

    @GetMapping("/scrapInfo")
    public List<BatchInfoDto> getScrapInfo() {
        return batchInfoService.getScrapInfo();
    }

}
