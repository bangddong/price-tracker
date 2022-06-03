package com.bd.tracker.core.dto;

import com.bd.tracker.core.constant.ScrapCategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BatchInfoResponse {

    private final Long id;
    private final String url;
    private final String cssQuery;
    private final ScrapCategory category;
    private final String productNm;

    // 불변객체를 deserialize 시 사용을 위해 별도 어노테이션으로 정의
    @JsonCreator
    public static BatchInfoResponse of(
            @JsonProperty("id") Long id,
            @JsonProperty("url") String url,
            @JsonProperty("cssQuery") String cssQuery,
            @JsonProperty("category") ScrapCategory category,
            @JsonProperty("productNm") String productNm
    ) {
        return new BatchInfoResponse(id, url, cssQuery, category, productNm);
    }

    public static BatchInfoResponse from(BatchInfoDto batchInfoDto) {
        if (batchInfoDto == null) return null;
        return BatchInfoResponse.of(
                batchInfoDto.getId(),
                batchInfoDto.getUrl(),
                batchInfoDto.getCssQuery(),
                batchInfoDto.getCategory(),
                batchInfoDto.getProductNm()
        );
    }

}
