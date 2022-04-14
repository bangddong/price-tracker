package com.bd.tracker.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PriceInfoDto {

    private final BatchInfoDto batchInfoDto;
    private final Integer price;

    public static PriceInfoDto of(BatchInfoDto batchInfoDto, Integer price) {
        return new PriceInfoDto(batchInfoDto, price);
    }
}
