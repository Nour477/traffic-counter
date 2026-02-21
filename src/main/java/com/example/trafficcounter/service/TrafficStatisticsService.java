package com.example.trafficcounter.service;

import com.example.trafficcounter.domain.TrafficRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrafficStatisticsService {

    public int calculateTotalCars(List<TrafficRecord> records) {
        return records.stream()
                .mapToInt(TrafficRecord::getCarCount)
                .sum();
    }

    public Map<LocalDate, Integer> calculateDailyTotals(List<TrafficRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getTimestamp().toLocalDate(),
                        Collectors.summingInt(TrafficRecord::getCarCount)
                ));
    }

    public List<TrafficRecord> findTop3(List<TrafficRecord> records) {
        return records.stream()
                .sorted(Comparator.comparingInt(TrafficRecord::getCarCount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<TrafficRecord> findLeastHourAndHalf(List<TrafficRecord> records) {
        if (records.size() <= 3) {
            return records;
        }

        List<TrafficRecord> sorted = new ArrayList<>(records);
        sorted.sort(Comparator.comparing(TrafficRecord::getTimestamp));

        int minSum = Integer.MAX_VALUE;
        List<TrafficRecord> leastPeriod = null;

        for (int i = 0; i <= sorted.size() - 3; i++) {
            List<TrafficRecord> window = sorted.subList(i, i + 3);
            int sum = window.stream().mapToInt(TrafficRecord::getCarCount).sum();
            if (sum < minSum) {
                minSum = sum;
                leastPeriod = new ArrayList<>(window);
            }
        }

        return leastPeriod != null ? leastPeriod : List.of();
    }
}
