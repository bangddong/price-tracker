package com.bd.tracker.core.dto;

import com.bd.tracker.core.entity.BatchInfo;
import com.bd.tracker.core.entity.ScrapInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScrapInfoDto {

    private final Long batchId;
    private final Integer price;

    public static ScrapInfoDto of(Long id, Integer price) {
        return new ScrapInfoDto(id, price);
    }

    public ScrapInfo toEntity(BatchInfo batchInfo) {
        return ScrapInfo.of(batchInfo, price);
    }

}
