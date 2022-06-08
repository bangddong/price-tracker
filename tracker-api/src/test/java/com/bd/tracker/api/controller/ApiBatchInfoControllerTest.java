package com.bd.tracker.api.controller;

import com.bd.tracker.api.constant.ErrorCode;
import com.bd.tracker.api.service.BatchInfoService;
import com.bd.tracker.core.constant.ScrapCategory;
import com.bd.tracker.core.dto.BatchInfoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiBatchInfoController.class)
class ApiBatchInfoControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BatchInfoService batchInfoService;

    @Test
    @DisplayName("[API][GET] 스크랩할 URL과 CSS 정보를 받아온다.")
    void givenNothing_whenRequestBatchInfo_thenReturnBatchInfo() throws Exception {
        //when & then
        mvc.perform(
                get("/api/batchInfo/" + ScrapCategory.COUPANG_PRICE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API][POST] 스크랩할 대상을 추가한다.")
    void givenBatchInfoRequest_whenCreateBatchInfo_thenReturnSuccess() throws Exception {
        //given
        BatchInfoRequest batchInfoRequest = BatchInfoRequest.of(
                "https://www.coupang.com/vp/products/2128457892?itemId=11356736068&vendorItemId=78633105449&q=%EC%95%A0%ED%94%8C%EC%9B%8C%EC%B9%98&itemsCount=36&searchId=10b355eda0e74e069a8c7e9763783fe8&rank=0&isAddedCart=",
                "total-price",
                ScrapCategory.COUPANG_PRICE,
                        "애플워치"
        );

        given(batchInfoService.createBatchInfo(any())).willReturn(true);

        //when & then
        mvc.perform(
                post("/api/batchInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(batchInfoRequest))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(batchInfoService).should().createBatchInfo(any());

    }

    @Test
    @DisplayName("[API][POST] 스크랩할 대상을 추가시 에러발생. 데이터 누락")
    void givenWrongBatchInfoRequest_whenCreateBatchInfo_thenReturnFail() throws Exception {
        //given
        BatchInfoRequest batchInfoRequest = BatchInfoRequest.of(
                "   ",
                "total-price",
                ScrapCategory.COUPANG_PRICE,
                "애플워치"
        );

        //when & then
        mvc.perform(
                        post("/api/batchInfo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(batchInfoRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()));
        then(batchInfoService).shouldHaveNoInteractions();

    }

}