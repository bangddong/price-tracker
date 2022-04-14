package com.bd.tracker.core.dto;

import com.bd.tracker.core.entity.BatchInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BatchInfoDto {

    private final Long id;
    private final String url;
    private final String cssQuery;
    private final String category;
    private final String productNm;
    private final String searchYn;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public static BatchInfoDto of(BatchInfo batchInfo) {
        return new BatchInfoDto(
                batchInfo.getId(),
                batchInfo.getUrl(),
                batchInfo.getCssQuery(),
                batchInfo.getCategory(),
                batchInfo.getProductNm(),
                batchInfo.getSearchYn(),
                batchInfo.getCreateAt(),
                batchInfo.getModifiedAt()
                );
    }

}
