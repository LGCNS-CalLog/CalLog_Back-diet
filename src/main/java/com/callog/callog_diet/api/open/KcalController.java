package com.callog.callog_diet.api.open;

import com.callog.callog_diet.common.dto.ApiResponseDto;
import com.callog.callog_diet.domain.dto.kcal.KcalResponse;
import com.callog.callog_diet.service.KcalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/diet/kcal", produces = MediaType.APPLICATION_JSON_VALUE)
public class KcalController {
    private final KcalService kcalService;

    // api 테스트
    @GetMapping(value = "/test")
    public ApiResponseDto<String> test() {
        String response = "kcal 테스트입니다.";
        return ApiResponseDto.createOk(response);
    }

    // 섭취 칼로리 변화 추이
    // 회원 섭취 칼로리 하루 단위(아침, 점심, 저녁 총 칼로리 합계)
    // (현재 날짜 - 6) ~ 현재 날짜
    @GetMapping(value = "")
    public ApiResponseDto<List<KcalResponse.KcalListResponse>> getCalorieIntakeTrend() {
        // TODO: API Gateway 필터로 사용자 정보 받아오기 (일단 하드코딩)
        Long userId = 1L;
        List<KcalResponse.KcalListResponse> response = kcalService.getCalorieIntakeTrend(userId);
        return ApiResponseDto.createOk(response);
    }

}
