package com.bd.tracker.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PriceInfoRequest {

    private final BatchInfoResponse batchInfoDto;
    private final Integer price;

    public static PriceInfoRequest of(BatchInfoResponse batchInfoResponse, Integer price) {
        return new PriceInfoRequest(batchInfoResponse, price);
    }
}
