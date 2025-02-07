package com.readings.domain;

public class SuspiciousReadingInfo {

    private final String clientId;
    private final String period;
    private final double value;
    private final double median;

    public SuspiciousReadingInfo(String clientId, String period, double value, double median) {
        this.clientId = clientId;
        this.period = period;
        this.value = value;
        this.median = median;
    }

    public String getClientId() {
        return clientId;
    }

    public String getPeriod() {
        return period;
    }

    public double getValue() {
        return value;
    }

    public double getMedian() {
        return median;
    }
}
