package com.bd.tracker.api.exception;

import com.bd.tracker.api.constant.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{

    private final ErrorCode errorCode;

    /**
     * 기본 에러메시지를 반환한다.
     */
    public GeneralException() {
        super(ErrorCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    /**
     * 사용자 정의 메시지를 반환한다.
     * @param message 사용자 정의 에러 메시지
     */
    public GeneralException(String message) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    /**
     * 에러 사유와 사용자 정의 메시지를 반환한다.
     * @param message 사용자 정의 에러 메시지
     * @param cause 에러
     */
    public GeneralException(String message, Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    /**
     * 에러 사유와 기본 에러 메시지를 반환한다.
     * @param cause 에러
     */
    public GeneralException(Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    /**
     * 정의된 에러코드에 해당하는 기본 에러 메시지를 반환한다.
     * @param errorCode ErrorCode 에 정의된 에러 코드
     */
    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 정의된 에러코드와 사용자 정의 에러 메시지를 반환한다.
     * @param errorCode ErrorCode 에 정의된 에러 코드
     * @param message 사용자 정의 에러 메시지
     */
    public GeneralException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    /**
     * 정의된 에러코드와 사용자 정의 에러 메시지와 에러 사유를 반환한다.
     * message 가 Null 이거나 Blank 일 경우 정의된 에러코드에 해당하는
     * 에러 메시지를 반환한다.
     * @param errorCode ErrorCode 에 정의된 에러 코드
     * @param message 사용자 정의 에러 메시지
     * @param cause 에러
     */
    public GeneralException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    /**
     * 정의된 에러 코드의 메시지와 에러 사유를 반환한다.
     * @param errorCode ErrorCode 에 정의된 에러 코드
     * @param cause 에러
     */
    public GeneralException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }

}
