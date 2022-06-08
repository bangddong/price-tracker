package com.bd.tracker.api.service;

import com.bd.tracker.api.constant.ErrorCode;
import com.bd.tracker.api.exception.GeneralException;
import com.bd.tracker.core.dto.ScrapInfoDto;
import com.bd.tracker.core.entity.BatchInfo;
import com.bd.tracker.core.repository.BatchInfoRepository;
import com.bd.tracker.core.repository.ScrapInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScrapInfoService {

    private final ScrapInfoRepository scrapInfoRepository;
    private final BatchInfoRepository batchInfoRepository;

    @Transactional
    public boolean createScrapInfo(List<ScrapInfoDto> scrapInfoDtoList) {
        try {
            if (scrapInfoDtoList == null || scrapInfoDtoList.size() == 0) {
                return false;
            }
            for (ScrapInfoDto dto : scrapInfoDtoList) {
                BatchInfo batchInfo = batchInfoRepository.getById(dto.getBatchId());
                scrapInfoRepository.save(dto.toEntity(batchInfo));
            }
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public void getScrapInfo() {

    }

}
