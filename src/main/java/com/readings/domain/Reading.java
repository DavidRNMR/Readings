package com.readings.domain;

public class Reading {

    private final String clientId;
    private final String period;
    private final double value;


    public Reading(String clientId, String period, double value) {
        this.clientId = clientId;
        this.period = period;
        this.value = value;
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
}
