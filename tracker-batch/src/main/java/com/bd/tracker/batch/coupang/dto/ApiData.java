package com.bd.tracker.batch.coupang.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApiData<T> {

    private final T data;

    @JsonCreator
    public ApiData(
            @JsonProperty("data") T data
    ) {
        this.data = data;
    }


}
