package com.bd.tracker.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScrapInfoRequest {

    private final List<ScrapInfoDto> priceInfoList;

    @JsonCreator
    public static ScrapInfoRequest of(
            @JsonProperty("priceInfoList") List<ScrapInfoDto> priceInfoList
    ) {
        return new ScrapInfoRequest(priceInfoList);
    }

}
