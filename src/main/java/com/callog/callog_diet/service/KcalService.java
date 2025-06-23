package com.callog.callog_diet.service;

import com.callog.callog_diet.domain.dto.kcal.KcalResponse;
import com.callog.callog_diet.domain.entity.Meal;
import com.callog.callog_diet.domain.repository.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KcalService {
    private final MealRepository mealRepository;

    public List<KcalResponse.KcalListResponse> getCalorieIntakeTrend(Long userId) {
        LocalDate lastDay = LocalDate.now();
        LocalDate firstDay = lastDay.minusDays(6);

        List<Meal> meals = mealRepository.findAllByUserIdAndDateBetween(userId, firstDay, lastDay);

        Map<LocalDate, Double> kcalSumByDate = meals.stream()
                .collect(Collectors.groupingBy(
                        Meal::getDate,
                        Collectors.summingDouble(m -> m.getKcal() != null ? m.getKcal() : 0.0)
                ));

        return kcalSumByDate.entrySet().stream()
                .map(entry -> KcalResponse.KcalListResponse.builder()
                        .date(entry.getKey())
                        .kcal(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(KcalResponse.KcalListResponse::getDate))
                .collect(Collectors.toList());
    }

}
