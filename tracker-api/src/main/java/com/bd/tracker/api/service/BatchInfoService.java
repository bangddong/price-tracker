package com.bd.tracker.api.service;

import com.bd.tracker.api.constant.ErrorCode;
import com.bd.tracker.api.exception.GeneralException;
import com.bd.tracker.core.constant.ScrapCategory;
import com.bd.tracker.core.dto.BatchInfoDto;
import com.bd.tracker.core.dto.BatchInfoRequest;
import com.bd.tracker.core.repository.BatchInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BatchInfoService {

    private final BatchInfoRepository batchInfoRepository;

    @Transactional
    public List<BatchInfoDto> getBatchInfo(ScrapCategory category) {
        return batchInfoRepository.findByCategory(category).stream()
                .filter(batchInfo -> batchInfo.getSearchYn().equals("Y"))
                .map(BatchInfoDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean createBatchInfo(BatchInfoRequest batchInfoRequest) {
        try {
            if (batchInfoRequest == null) {
                return false;
            }

            batchInfoRepository.save(batchInfoRequest.toEntity());
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

}
