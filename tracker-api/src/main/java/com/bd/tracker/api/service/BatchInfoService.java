package com.bd.tracker.api.service;

import com.bd.tracker.core.dto.BatchInfoDto;
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
    public List<BatchInfoDto> getScrapInfo() {
        return batchInfoRepository.findAllBySearchYn("Y").stream() // TODO 나중에 하드코딩 지우자
                .map(BatchInfoDto::of)
                .collect(Collectors.toList());
    }

}
