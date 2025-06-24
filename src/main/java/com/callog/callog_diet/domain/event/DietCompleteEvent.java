package com.callog.callog_diet.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DietCompleteEvent {
    public static final String Topic = "diet-complete";

    private String action;
    private String userId; // email 양식
    private String nickName;
    private String subject; // 메일 제목 (date, mealType)
    private String message;  // 메일 메세지 (foods: foodName, amount)
}
