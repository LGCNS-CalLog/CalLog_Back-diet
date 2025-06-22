package com.callog.callog_diet.common.exception;

public class CustomException extends RuntimeException {
    private final ErrorCodeInterface errorCode;

    public CustomException(ErrorCodeInterface errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCodeInterface getErrorCode() {
        return errorCode;
    }
}
