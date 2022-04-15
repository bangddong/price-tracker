package com.bd.tracker.core.dto;

import com.bd.tracker.core.entity.BatchInfo;
import com.bd.tracker.core.entity.PriceInfo;
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

    public PriceInfo toEntity(BatchInfo batchInfo) {
        return PriceInfo.of(batchInfo, price);
    }

}
