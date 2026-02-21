package com.example.trafficcounter.domain;

import java.time.LocalDateTime;

public class TrafficRecord {

    private final LocalDateTime timestamp;
    private final int carCount;

    public TrafficRecord(LocalDateTime timestamp, int carCount) {
        this.timestamp = timestamp;
        this.carCount = carCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getCarCount() {
        return carCount;
    }
}
