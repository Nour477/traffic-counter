package com.example.trafficcounter.service;

import com.example.trafficcounter.domain.TrafficRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrafficStatisticsServiceTest {

    private TrafficStatisticsService service;

    @BeforeEach
    void setUp() {
        service = new TrafficStatisticsService();
    }

    @Test
    void calculatesTotalCars() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 5),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 12),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 6, 0, 0), 14)
        );

        int total = service.calculateTotalCars(records);

        assertEquals(31, total);
    }

    @Test
    void totalCarsWithEmptyList() {
        List<TrafficRecord> records = List.of();

        int total = service.calculateTotalCars(records);

        assertEquals(0, total);
    }

    @Test
    void calculatesDailyTotals() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 5),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 12),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 6, 0, 0), 14),
                new TrafficRecord(LocalDateTime.of(2021, 12, 2, 8, 0, 0), 20),
                new TrafficRecord(LocalDateTime.of(2021, 12, 2, 8, 30, 0), 15)
        );

        Map<LocalDate, Integer> dailyTotals = service.calculateDailyTotals(records);

        assertEquals(2, dailyTotals.size());
        assertEquals(31, dailyTotals.get(LocalDate.of(2021, 12, 1)));
        assertEquals(35, dailyTotals.get(LocalDate.of(2021, 12, 2)));
    }

    @Test
    void dailyTotalsWithZeroCounts() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 0),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 0)
        );

        Map<LocalDate, Integer> dailyTotals = service.calculateDailyTotals(records);

        assertEquals(1, dailyTotals.size());
        assertEquals(0, dailyTotals.get(LocalDate.of(2021, 12, 1)));
    }

    @Test
    void findsTop3HalfHours() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 5),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 46),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 6, 0, 0), 14),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 6, 30, 0), 42),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 7, 0, 0), 25)
        );

        List<TrafficRecord> top3 = service.findTop3(records);

        assertEquals(3, top3.size());
        assertEquals(46, top3.get(0).getCarCount());
        assertEquals(42, top3.get(1).getCarCount());
        assertEquals(25, top3.get(2).getCarCount());
    }

    @Test
    void top3WithLessThan3Records() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 10),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 20)
        );

        List<TrafficRecord> top3 = service.findTop3(records);

        assertEquals(2, top3.size());
        assertEquals(20, top3.get(0).getCarCount());
        assertEquals(10, top3.get(1).getCarCount());
    }

    @Test
    void findsLeastHourAndHalf() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 5),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 12),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 6, 0, 0), 14),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 6, 30, 0), 15),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 7, 0, 0), 25)
        );

        List<TrafficRecord> leastPeriod = service.findLeastHourAndHalf(records);

        assertEquals(3, leastPeriod.size());
        int sum = leastPeriod.stream().mapToInt(TrafficRecord::getCarCount).sum();
        assertEquals(31, sum);
        assertEquals(LocalDateTime.of(2021, 12, 1, 5, 0, 0), leastPeriod.get(0).getTimestamp());
    }

    @Test
    void leastHourAndHalfPeriodWithLessThan3Records() {
        List<TrafficRecord> records = List.of(
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 5),
                new TrafficRecord(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 12)
        );

        List<TrafficRecord> leastPeriod = service.findLeastHourAndHalf(records);

        assertEquals(2, leastPeriod.size());
    }
}
