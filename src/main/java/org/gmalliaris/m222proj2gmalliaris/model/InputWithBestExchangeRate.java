package org.gmalliaris.m222proj2gmalliaris.model;

public class InputWithBestExchangeRate {

    private final String recipient;
    private final long value;
    private final double valueUsd;

    public InputWithBestExchangeRate(String recipient, long value, double valueUsd) {
        this.recipient = recipient;
        this.value = value;
        this.valueUsd = valueUsd;
    }

    public String getRecipient() {
        return recipient;
    }

    public long getValue() {
        return value;
    }

    public double getValueUsd() {
        return valueUsd;
    }
}
