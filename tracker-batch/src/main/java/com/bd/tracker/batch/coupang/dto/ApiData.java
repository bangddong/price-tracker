package com.bd.tracker.batch.coupang.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiData<T> {

    private final T data;

    @JsonCreator
    public ApiData<T> of(
            @JsonProperty("data") T data
    ) {
        return new ApiData<>(data);
    }


}
