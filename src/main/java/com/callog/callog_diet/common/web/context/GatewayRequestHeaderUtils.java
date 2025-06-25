package com.callog.callog_diet.common.web.context;


import com.callog.callog_diet.common.exception.CustomException;
import com.callog.callog_diet.common.exception.DietErrorCode;
import com.callog.callog_diet.common.exception.ErrorCode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GatewayRequestHeaderUtils {
    public static String getRequestHeaderParamAsString(String key) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getHeader(key);
    }
    public static String getUserId() {
        return getRequestHeaderParamAsString("X-Auth-User-Id");
    }
    public static String getClientDevice() {
        return getRequestHeaderParamAsString("X-Client-Device");
    }
    public static String getClientAddress() {
        return getRequestHeaderParamAsString("X-Client-Address");
    }

    public static String getUserIdOrThrowException() {
        String userId = getUserId();
        if (userId == null) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return userId;
    }
    public static String getClientDeviceOrThrowException() {
        String clientDevice = getClientDevice();
        if (clientDevice == null) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return clientDevice;
    }
    public static String getClientAddressOrThrowException() {
        String clientAddress = getClientAddress();
        if (clientAddress == null) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return clientAddress;
    }
}

