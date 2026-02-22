package com.example.trafficcounter.service;

import com.example.trafficcounter.domain.TrafficRecord;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

        PriorityQueue<TrafficRecord> heap =
                new PriorityQueue<>(Comparator.comparingInt(TrafficRecord::getCarCount));

        for (TrafficRecord record : records) {
            heap.offer(record);
            if (heap.size() > 3) {
                heap.poll();
            }
        }

        return heap.stream()
                .sorted(Comparator.comparingInt(TrafficRecord::getCarCount).reversed())
                .toList();
    }

        public List<TrafficRecord> findLeastHourAndHalf(List<TrafficRecord> records) {
            if (records.size() <= 3) {
                return records;
            }
            int currentSum = records.subList(0, 3).stream().mapToInt(TrafficRecord::getCarCount).sum();
            int minSum = currentSum;
            int minStartIndex = 0;
            for (int i = 3; i < records.size(); i++) {
                currentSum += records.get(i).getCarCount() - records.get(i - 3).getCarCount();
                if (currentSum < minSum) {
                    minSum = currentSum;
                    minStartIndex = i - 2;
                }
            }
        return records.subList(minStartIndex, minStartIndex + 3);
        }
}
