package com.example.trafficcounter.parser;

import com.example.trafficcounter.domain.TrafficRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrafficFileParserTest {

    private final TrafficFileParser parser = new TrafficFileParser();

    @Test
    void parsesSingleLine() throws IOException {
        Path file = Files.createTempFile("traffic", ".txt");
        Files.writeString(file, "2021-12-01T05:00:00 5");

        List<TrafficRecord> records = parser.parse(file);

        assertEquals(1, records.size());
        assertEquals(LocalDateTime.of(2021, 12, 1, 5, 0, 0), records.get(0).getTimestamp());
        assertEquals(5, records.get(0).getCarCount());
        Files.deleteIfExists(file);
    }

    @Test
    void parsesMultipleLines() throws IOException {
        Path file = Files.createTempFile("traffic", ".txt");
        Files.writeString(file, """
            2021-12-01T05:00:00 5
            2021-12-01T05:30:00 12
            2021-12-01T06:00:00 14
            """);

        List<TrafficRecord> records = parser.parse(file);

        assertEquals(3, records.size());
        assertEquals(5, records.get(0).getCarCount());
        assertEquals(12, records.get(1).getCarCount());
        assertEquals(14, records.get(2).getCarCount());
        Files.deleteIfExists(file);
    }

    @Test
    void emptyFileReturnsEmptyList() throws IOException {
        Path file = Files.createTempFile("traffic", ".txt");

        List<TrafficRecord> records = parser.parse(file);

        assertTrue(records.isEmpty());
        Files.deleteIfExists(file);
    }
}
