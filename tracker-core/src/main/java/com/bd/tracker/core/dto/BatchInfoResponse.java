package com.bd.tracker.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BatchInfoResponse {

    private final List<BatchInfoResponseDto> responseDtoList;

    @JsonCreator
    public static BatchInfoResponse of(
            @JsonProperty("responseDtoList") List<BatchInfoResponseDto> responseDtoList
    ) {
        return new BatchInfoResponse(responseDtoList);
    }

}
