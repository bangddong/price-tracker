package com.bd.tracker.core.dto;

import com.bd.tracker.core.constant.ScrapCategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BatchInfoResponse {

    private final Long id;
    private final String url;
    private final String cssQuery;
    private final ScrapCategory category;
    private final String productNm;

    public static BatchInfoResponse of(
            Long id,
            String url,
            String cssQuery,
            ScrapCategory category,
            String productNm
    ) {
        return new BatchInfoResponse(id, url, cssQuery, category, productNm);
    }

    // 불변객체를 deserialize 시 사용을 위해 별도 어노테이션으로 정의
    @JsonCreator
    public BatchInfoResponse (
            @JsonProperty("id") Long id,
            @JsonProperty("url") String url,
            @JsonProperty("cssQuery") String cssQuery,
            @JsonProperty("category") ScrapCategory category,
            @JsonProperty("productNm") String productNm
    ) {
        this.id = id;
        this.url = url;
        this.cssQuery = cssQuery;
        this.category = category;
        this.productNm = productNm;
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
