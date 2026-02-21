package com.example.trafficcounter.parser;

import com.example.trafficcounter.domain.TrafficRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrafficFileParser {

    private static final String LINE_SEPARATOR = " ";

    public List<TrafficRecord> parse(Path path) throws IOException {
        List<TrafficRecord> records = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            int lastSpace = line.lastIndexOf(LINE_SEPARATOR);
            if (lastSpace <= 0) {
                continue;
            }
            String timestampPart = line.substring(0, lastSpace).trim();
            String countPart = line.substring(lastSpace + 1).trim();
            LocalDateTime timestamp = LocalDateTime.parse(timestampPart);
            int carCount = Integer.parseInt(countPart);
            records.add(new TrafficRecord(timestamp, carCount));
        }
        return records;
    }
}
