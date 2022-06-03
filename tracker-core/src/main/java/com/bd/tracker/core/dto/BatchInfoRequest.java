package com.bd.tracker.core.dto;

import com.bd.tracker.core.constant.ScrapCategory;
import com.bd.tracker.core.entity.BatchInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class BatchInfoRequest {

    @NotBlank
    private final String url;
    @NotBlank
    private final String cssQuery;

    // TODO : enum custom valid 도입 검토필요
    @NotNull
    private final ScrapCategory category;
    @NotBlank
    private final String productNm;

    public BatchInfo toEntity() {
        return BatchInfo.of(url, cssQuery, category, productNm);
    }

    public static BatchInfoRequest of(String url, String cssQuery, ScrapCategory category, String productNm) {
        return new BatchInfoRequest(url, cssQuery, category, productNm);
    }

}
