package com.callog.callog_diet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodErrorCode implements ErrorCodeInterface{
    // 400
    INVALID_INPUT("FOOD400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD("DIET400", "필수 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),

    // 401
    UNAUTHORIZED("FOOD401", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),

    // 403
    FORBIDDEN("FOOD403", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 404
    FOOD_NOT_FOUND("FOOD404", "해당하는 음식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 500
    INTERNAL_ERROR("FOOD500", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
