package com.bd.tracker.api.dto;

import com.bd.tracker.api.constant.ErrorCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiDataResponse<T> extends ApiErrorResponse {

    private final T data;

    private ApiDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> ApiDataResponse<T> of(T data) {
        return new ApiDataResponse<>(data);
    }

    public static <T> ApiDataResponse<T> empty() {
        return new ApiDataResponse<>(null);
    }

}
