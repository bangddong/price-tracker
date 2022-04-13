package com.bd.tracker.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PriceInfoDto {

    private final Long id;
    private final BatchInfoDto batchInfoDto;
    private final Integer price;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

}
