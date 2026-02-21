package com.example.trafficcounter;

import com.example.trafficcounter.domain.TrafficRecord;
import com.example.trafficcounter.parser.TrafficFileParser;
import com.example.trafficcounter.service.TrafficStatisticsService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrafficCounterRunner {

    private final TrafficFileParser parser;
    private final TrafficStatisticsService statisticsService;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public TrafficCounterRunner() {
        this.parser = new TrafficFileParser();
        this.statisticsService = new TrafficStatisticsService();
    }

    public void run(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<TrafficRecord> records = parser.parse(path);

        if (records.isEmpty()) {
            System.out.println("No traffic records found in file.");
            return;
        }

        int totalCars = statisticsService.calculateTotalCars(records);
        var dailyTotals = statisticsService.calculateDailyTotals(records);
        List<TrafficRecord> top3 = statisticsService.findTop3(records);
        List<TrafficRecord> leastPeriod = statisticsService.findLeastHourAndHalf(records);

        System.out.println("totalCars: " + totalCars);
        System.out.println();

        System.out.println("dailyTotals:");
        dailyTotals.forEach((date, count) -> System.out.println(date + " " + count));
        System.out.println();

        System.out.println("top 3:");
        top3.forEach(record -> System.out.println(record.getTimestamp().format(TIMESTAMP_FORMATTER) + " " + record.getCarCount()));
        System.out.println();

        System.out.println("least Hour And Half:");
        leastPeriod.forEach(record -> System.out.println(record.getTimestamp().format(TIMESTAMP_FORMATTER) + " " + record.getCarCount()));
        System.out.println();
    }
}
