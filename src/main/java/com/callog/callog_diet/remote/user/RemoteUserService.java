package com.callog.callog_diet.remote.user;

import com.callog.callog_diet.remote.user.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "callog-user", path = "/user/info")
public interface RemoteUserService {
    @GetMapping(value="")
    public UserInfoDto.Response getUserInfo(@RequestParam Long userId);
}
