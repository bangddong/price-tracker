package com.bd.tracker.core.dto;

import com.bd.tracker.core.entity.BatchInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BatchInfoRequest {

    private final String url;
    private final String cssQuery;
    private final String category;
    private final String productNm;

    public BatchInfo toEntity() {
        return BatchInfo.of(url, cssQuery, category, productNm);
    }

}
