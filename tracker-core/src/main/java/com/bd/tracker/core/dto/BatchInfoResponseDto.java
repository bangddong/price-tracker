package com.bd.tracker.core.dto;

import com.bd.tracker.core.constant.ScrapCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BatchInfoResponseDto {

    private final Long id;
    private final String url;
    private final String cssQuery;
    private final ScrapCategory category;
    private final String productNm;

    public static BatchInfoResponseDto from(BatchInfoDto batchInfoDto) {
        if (batchInfoDto == null) return null;
        return new BatchInfoResponseDto(
                batchInfoDto.getId(),
                batchInfoDto.getUrl(),
                batchInfoDto.getCssQuery(),
                batchInfoDto.getCategory(),
                batchInfoDto.getProductNm()
        );
    }

}
