package com.callog.callog_diet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DietErrorCode implements ErrorCodeInterface{
    // 400
    DIET_INVALID_INPUT("DIET400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD("DIET400", "필수 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),

    // 401
    DIET_UNAUTHORIZED("DIET401", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),

    // 403
    DIET_FORBIDDEN("DIET403", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 404
    DIET_NOT_FOUND("DIET404", "해당 식단 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 500
    DIET_INTERNAL_ERROR("DIET500", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
