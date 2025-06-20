package com.callog.callog_diet.api.open;

import com.callog.callog_diet.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/food/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FoodController {
    @GetMapping(value = "/test")
    public ApiResponseDto<String> test() {
        String response = "테스트입니다.";
        return ApiResponseDto.createOk(response);
    }
}
