package org.gmalliaris.m222proj2gmalliaris.model;

public class OutputResponse {

    private final long time;
    private final long value;
    private final double valueUsd;
    private final String recipient;
    private final String type;
    private final Boolean isSpendable;

    public OutputResponse(long time, long value, double valueUsd, String recipient, String type, Boolean isSpendable) {
        this.time = time;
        this.value = value;
        this.valueUsd = valueUsd;
        this.recipient = recipient;
        this.type = type;
        this.isSpendable = isSpendable;
    }

    public long getTime() {
        return time;
    }

    public long getValue() {
        return value;
    }

    public double getValueUsd() {
        return valueUsd;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getType() {
        return type;
    }

    public Boolean getSpendable() {
        return isSpendable;
    }
}
