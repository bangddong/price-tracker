package com.bd.tracker.batch.service;

import com.bd.tracker.core.dto.BatchInfoResponse;

import java.util.List;

public interface ApiWebClientService {
    List<BatchInfoResponse> getBatchInfo();
    void sendScrapResult();
}
