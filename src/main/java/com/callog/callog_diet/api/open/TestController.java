package com.callog.callog_diet.api.open;

import com.callog.callog_diet.common.dto.ApiResponseDto;
import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.event.scheduler.DietEventScheduler;
import com.callog.callog_diet.remote.user.RemoteUserService;
import com.callog.callog_diet.remote.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestController {
    private final RemoteUserService remoteUserService;
    private final DietEventScheduler kafkaTestService;

    // remote 테스트 (from diet to user)
    @GetMapping(value = "/test")
    public ApiResponseDto<UserInfoDto.Response> remoteTest() {
        UserInfoDto.Response user = remoteUserService.getUserInfo(1L);
        return ApiResponseDto.createOk(user);
    }

    // kafka 테스트 (scheduler 메소드 테스트)
    @GetMapping(value = "/kafka/test")
    public ApiResponseDto<String> kafkaTest() {
        kafkaTestService.runMealNotificationByType(MealType.DINNER);
        return ApiResponseDto.defaultOk();
    }


}
