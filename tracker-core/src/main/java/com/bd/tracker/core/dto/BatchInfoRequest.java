package com.bd.tracker.core.dto;

import com.bd.tracker.core.entity.BatchInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class BatchInfoRequest {

    @NotBlank
    private final String url;
    @NotBlank
    private final String cssQuery;
    @NotBlank
    private final String category;
    @NotBlank
    private final String productNm;

    public BatchInfo toEntity() {
        return BatchInfo.of(url, cssQuery, category, productNm);
    }

}
