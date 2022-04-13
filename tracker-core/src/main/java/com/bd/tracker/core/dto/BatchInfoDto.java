package com.bd.tracker.core.dto;

import com.bd.tracker.core.entity.BatchInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BatchInfoDto {

    private final Long id;
    private final String url;
    private final String cssQuery;
    private final String searchYn;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public static BatchInfoDto of(BatchInfo batchInfo) {
        return new BatchInfoDto(
                batchInfo.getId(),
                batchInfo.getUrl(),
                batchInfo.getCssQuery(),
                batchInfo.getSearchYn(),
                batchInfo.getCreateAt(),
                batchInfo.getModifiedAt()
                );
    }

    // 불변객체를 deserialize 시 사용을 위해 별도 어노테이션으로 정의
    @JsonCreator
    public static BatchInfoDto of(
            @JsonProperty("id") Long id,
            @JsonProperty("url") String url,
            @JsonProperty("cssQuery") String cssQuery,
            @JsonProperty("searchYn") String searchYn,
            @JsonProperty("createAt") LocalDateTime createAt,
            @JsonProperty("modifiedAt") LocalDateTime modifiedAt
    ) {
        return new BatchInfoDto(id, url, cssQuery, searchYn, createAt, modifiedAt);
    }

}
