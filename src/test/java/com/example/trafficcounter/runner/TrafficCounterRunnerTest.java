package com.example.trafficcounter.runner;

import com.example.trafficcounter.TrafficCounterRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class TrafficCounterRunnerTest {

    @Test
    void processesSampleFile() throws IOException {
        Path file = Files.createTempFile("traffic", ".txt");
        Files.writeString(file, """
            2021-12-01T05:00:00 5
            2021-12-01T05:30:00 12
            2021-12-01T06:00:00 14
            2021-12-01T06:30:00 15
            2021-12-01T07:00:00 25
            2021-12-01T07:30:00 46
            2021-12-01T08:00:00 42
            2021-12-02T09:00:00 20
            2021-12-02T09:30:00 15
            """);

        TrafficCounterRunner runner = new TrafficCounterRunner();
        assertDoesNotThrow(() -> runner.run(file.toString()));
        Files.deleteIfExists(file);
    }

    @Test
    void handlesEmptyFile() throws IOException {
        Path file = Files.createTempFile("traffic", ".txt");

        TrafficCounterRunner runner = new TrafficCounterRunner();
        assertDoesNotThrow(() -> runner.run(file.toString()));
        Files.deleteIfExists(file);
    }
}
