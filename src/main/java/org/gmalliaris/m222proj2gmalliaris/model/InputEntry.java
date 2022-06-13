package org.gmalliaris.m222proj2gmalliaris.model;

public class InputEntry {

    private final long blockId;
    private final String transactionHash;
    private final String time;
    private final long value;
    private final double valueUsd;
    private final String recipient;
    private final String type;
    private final Boolean isSpendable;
    private final long spendingBlockId;
    private final String spendingTransactionHash;
    private final String spendingTime;
    private final double spendingValueUsd;
    private final long lifespan;

    public InputEntry(long blockId, String transactionHash, String time, long value, double valueUsd,
                      String recipient, String type, Boolean isSpendable, long spendingBlockId,
                      String spendingTransactionHash, String spendingTime, double spendingValueUsd,
                      long lifespan) {
        this.blockId = blockId;
        this.transactionHash = transactionHash;
        this.time = time;
        this.value = value;
        this.valueUsd = valueUsd;
        this.recipient = recipient;
        this.type = type;
        this.isSpendable = isSpendable;
        this.spendingBlockId = spendingBlockId;
        this.spendingTransactionHash = spendingTransactionHash;
        this.spendingTime = spendingTime;
        this.spendingValueUsd = spendingValueUsd;
        this.lifespan = lifespan;
    }

    public long getBlockId() {
        return blockId;
    }

    public String getTransactionHash() {
        return transactionHash;
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

    public long getSpendingBlockId() {
        return spendingBlockId;
    }

    public String getSpendingTransactionHash() {
        return spendingTransactionHash;
    }

    public String getSpendingTime() {
        return spendingTime;
    }

    public double getSpendingValueUsd() {
        return spendingValueUsd;
    }

    public long getLifespan() {
        return lifespan;
    }

    public Boolean getSpendable() {
        return isSpendable;
    }

    @Override
    public String toString() {
        return "InputEntry{" +
                "blockId=" + blockId +
                ", transactionHash='" + transactionHash + '\'' +
                ", time='" + time + '\'' +
                ", value=" + value +
                ", valueUsd=" + valueUsd +
                ", recipient='" + recipient + '\'' +
                ", type='" + type + '\'' +
                ", isSpendable=" + isSpendable +
                ", spendingBlockId=" + spendingBlockId +
                ", spendingTransactionHash='" + spendingTransactionHash + '\'' +
                ", spendingTime='" + spendingTime + '\'' +
                ", spendingValueUsd=" + spendingValueUsd +
                ", lifespan=" + lifespan +
                '}';
    }
}
