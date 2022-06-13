package org.gmalliaris.m222proj2gmalliaris.model;

public class OutputEntry {

    private final long blockId;
    private final String transactionsHash;
    private final String time;
    private final long value;
    private final double valueUsd;
    private final String recipient;
    private final String type;
    private final Boolean isSpendable;

    public OutputEntry(long blockId, String transactionsHash, String time, long value, double valueUsd, String recipient, String type, Boolean isSpendable) {
        this.blockId = blockId;
        this.transactionsHash = transactionsHash;
        this.time = time;
        this.value = value;
        this.valueUsd = valueUsd;
        this.recipient = recipient;
        this.type = type;
        this.isSpendable = isSpendable;
    }

    public long getBlockId() {
        return blockId;
    }

    public String getTransactionsHash() {
        return transactionsHash;
    }

    public String getTime() {
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

    public Boolean getIsSpendable() {
        return isSpendable;
    }

    @Override
    public String toString() {
        return "OutputEntry{" +
                "blockId=" + blockId +
                ", transactionsHash='" + transactionsHash + '\'' +
                ", time='" + time + '\'' +
                ", value=" + value +
                ", valueUsd=" + valueUsd +
                ", recipient='" + recipient + '\'' +
                ", type='" + type + '\'' +
                ", isSpendable=" + isSpendable +
                '}';
    }
}
