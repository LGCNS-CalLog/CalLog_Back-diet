package com.callog.callog_diet.domain.dto.kcal;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class KcalResponse {

    @Data
    @Builder
    public static class KcalListResponse {
        private LocalDate date;
        private Double kcal;
    }
}
