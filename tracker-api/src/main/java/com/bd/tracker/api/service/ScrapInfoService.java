package com.bd.tracker.api.service;

import com.bd.tracker.core.dto.ScrapInfoDto;
import com.bd.tracker.core.entity.BatchInfo;
import com.bd.tracker.core.repository.BatchInfoRepository;
import com.bd.tracker.core.repository.ScrapInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScrapInfoService {

    private final ScrapInfoRepository scrapInfoRepository;
    private final BatchInfoRepository batchInfoRepository;

    @Transactional
    public void createScrapInfo(ScrapInfoDto scrapInfoDto) {
        BatchInfo batchInfo = batchInfoRepository.getById(scrapInfoDto.getBatchId());
        scrapInfoRepository.save(scrapInfoDto.toEntity(batchInfo));
    }

    public void getScrapInfo() {

    }

}
