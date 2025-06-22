package com.callog.callog_diet.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
