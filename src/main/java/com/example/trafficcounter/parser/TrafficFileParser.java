package com.example.trafficcounter.parser;

import com.example.trafficcounter.domain.TrafficRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrafficFileParser {

    private static final char LINE_SEPARATOR = ' ';

    public List<TrafficRecord> parse(Path path) throws IOException {
        List<TrafficRecord> records = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)){
            String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    int lastSpace = line.lastIndexOf(LINE_SEPARATOR);
                    if (lastSpace <= 0) {
                        continue;
                    }
                    LocalDateTime timestamp = LocalDateTime.parse(line.substring(0, lastSpace));
                    int carCount = Integer.parseInt(line.substring(lastSpace + 1));
                    records.add(new TrafficRecord(timestamp, carCount));
                }
            }
        return records;
    }
}
