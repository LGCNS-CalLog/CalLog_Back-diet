package com.callog.callog_diet.api.open;

import com.callog.callog_diet.common.dto.ApiResponseDto;
import com.callog.callog_diet.domain.dto.food.FoodResponse;
import com.callog.callog_diet.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/food/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FoodController {

    @Autowired
    FoodService foodService;

    // api 테스트
    @GetMapping(value = "/test")
    public ApiResponseDto<String> test() {
        String response = "테스트입니다.";
        return ApiResponseDto.createOk(response);
    }

    // 음식 검색
    @GetMapping(value = "/food")
    public ApiResponseDto<List<FoodResponse.FoodListResponse>> food(@RequestParam(required = false) String search) {
        log.info("foodController: /food 호출 {}", search);
        List<FoodResponse.FoodListResponse> foodList = foodService.getFoodList(search);
        return ApiResponseDto.createOk(foodList);
    }
}
