package com.bd.tracker.batch.service;

import com.bd.tracker.core.dto.BatchInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApiWebClientService {
    List<BatchInfoResponse> getBatchInfo();
    void sendScrapResult();
}
