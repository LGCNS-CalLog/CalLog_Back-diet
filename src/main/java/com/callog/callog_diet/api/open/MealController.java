package com.callog.callog_diet.api.open;

import com.callog.callog_diet.common.dto.ApiResponseDto;
import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.dto.meal.MealRequest;
import com.callog.callog_diet.domain.dto.meal.MealResponse;
import com.callog.callog_diet.service.MealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/diet/meal", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealController {
    private final MealService mealService;

    // api 테스트
    @GetMapping(value = "/test")
    public ApiResponseDto<String> test() {
        String response = "meal 테스트입니다.";
        return ApiResponseDto.createOk(response);
    }

    // create: 식단 기록
    @PostMapping(value = "")
    public ApiResponseDto<MealResponse.CreateUpdateMealResponse> createMeal(
            @RequestBody MealRequest.MealCreateRequest request,
            @RequestHeader("X-Auth-User-Id") Long userId) {
        MealResponse.CreateUpdateMealResponse response = mealService.createMeal(userId, request);
        return ApiResponseDto.createOk(response);
    }

    // read: 식단 기록 전체 조회 (달력, 월단위)
    @GetMapping(value="/all")
    public ApiResponseDto<List<LocalDate>> readMonthMealList(
            @RequestParam(required = false) YearMonth yearMonth,
            @RequestHeader("X-Auth-User-Id") Long userId){
        yearMonth = getOrCurrentMonth(yearMonth);
        List<LocalDate> response = mealService.readMonthMealList(userId, yearMonth);
        return ApiResponseDto.createOk(response);
    }

    // read: 특정 날짜 식단 기록 조회 (아침, 점심, 저녁 한번에)
    @GetMapping(value = "")
    public ApiResponseDto<List<MealResponse.DateMealListResponse>> readDateMealList(
            @RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader("X-Auth-User-Id") Long userId) {
        date = getOrToday(date);
        List<MealResponse.DateMealListResponse> response = mealService.readDateMealList(userId, date);
        return ApiResponseDto.createOk(response);
    }

    // read: 특정 날짜 특정 mealType 식단 조회 (음식 수정 화면에서 사용 예정)
    @GetMapping(value = "/type")
    public ApiResponseDto<List<MealResponse.DateMealTypeListResponse>> readDateMealTypeList(
            @RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam MealType mealType,
            @RequestHeader("X-Auth-User-Id") Long userId) {
        date = getOrToday(date);
        List<MealResponse.DateMealTypeListResponse> response = mealService.readDateMealTypeList(userId, date, mealType);
        return ApiResponseDto.createOk(response);
    }

    // update: 식단 기록 수정
    @PostMapping(value = "/update")
    public ApiResponseDto<MealResponse.CreateUpdateMealResponse> updateMeal(
            @RequestBody MealRequest.MealUpdateRequest request,
            @RequestHeader("X-Auth-User-Id") Long userId) {
        // request: {id, userId, amount}
        MealResponse.CreateUpdateMealResponse response = mealService.updateMeal(userId, request);
        return ApiResponseDto.createOk(response);
    }

    // delete: 식단 기록 삭제
    @PostMapping(value = "/delete")
    public ApiResponseDto<String> deleteMeal(
            @RequestBody MealRequest.MealDeleteRequest request,
            @RequestHeader("X-Auth-User-Id") Long userId) {
        mealService.deleteMeal(userId, request);
        return ApiResponseDto.defaultOk();
    }

    // Date Null일 경우, 오늘 날짜 주입
    private LocalDate getOrToday(LocalDate date) {
        return (date != null) ? date : LocalDate.now();
    }

    private YearMonth getOrCurrentMonth(YearMonth yearMonth) {
        return (yearMonth != null) ? yearMonth : YearMonth.now();
    }


}
