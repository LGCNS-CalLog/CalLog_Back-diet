package com.callog.callog_diet.api.open;

import com.callog.callog_diet.common.dto.ApiResponseDto;
import com.callog.callog_diet.domain.dto.food.FoodResponse;
import com.callog.callog_diet.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/diet/food", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    // api 테스트
    @GetMapping(value = "/test")
    public ApiResponseDto<String> test() {
        String response = "테스트입니다.";
        return ApiResponseDto.createOk(response);
    }

    // 음식 검색
    @GetMapping(value = "")
    public ApiResponseDto<FoodResponse.FoodListWithCountResponse> food(@RequestParam(required = false) String search) {
        log.info("foodController: /food 호출 {}", search);
        FoodResponse.FoodListWithCountResponse foodList = foodService.getFoodListWithCount(search);
        return ApiResponseDto.createOk(foodList);
    }
}
