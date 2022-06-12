package org.gmalliaris.m222proj2gmalliaris.model;

public class TransactionEntry {

    private final long blockId;
    private final String hash;
    private final String time;
    private final long size;
    private final long weight;
    private final boolean isCoinbase;
    private final boolean hasWitness;
    private final long inputTotal;
    private final double inputTotalUsd;
    private final long outputTotal;
    private final double outputTotalUsd;
    private final long fee;
    private final double feeUsd;
    private final double feePerKwu;
    private final double feePerKwuUsd;

    public TransactionEntry(long blockId, String hash, String time, long size, long weight,
                            boolean isCoinbase, boolean hasWitness, long inputTotal,
                            double inputTotalUsd, long outputTotal, double outputTotalUsd,
                            long fee, double feeUsd, double feePerKwu, double feePerKwuUsd) {
        this.blockId = blockId;
        this.hash = hash;
        this.time = time;
        this.size = size;
        this.weight = weight;
        this.isCoinbase = isCoinbase;
        this.hasWitness = hasWitness;
        this.inputTotal = inputTotal;
        this.inputTotalUsd = inputTotalUsd;
        this.outputTotal = outputTotal;
        this.outputTotalUsd = outputTotalUsd;
        this.fee = fee;
        this.feeUsd = feeUsd;
        this.feePerKwu = feePerKwu;
        this.feePerKwuUsd = feePerKwuUsd;
    }

    public long getBlockId() {
        return blockId;
    }

    public String getHash() {
        return hash;
    }

    public String getTime() {
        return time;
    }

    public long getSize() {
        return size;
    }

    public long getWeight() {
        return weight;
    }

    public boolean isCoinbase() {
        return isCoinbase;
    }

    public boolean isHasWitness() {
        return hasWitness;
    }

    public long getInputTotal() {
        return inputTotal;
    }

    public double getInputTotalUsd() {
        return inputTotalUsd;
    }

    public long getOutputTotal() {
        return outputTotal;
    }

    public double getOutputTotalUsd() {
        return outputTotalUsd;
    }

    public long getFee() {
        return fee;
    }

    public double getFeeUsd() {
        return feeUsd;
    }

    public double getFeePerKwu() {
        return feePerKwu;
    }

    public double getFeePerKwuUsd() {
        return feePerKwuUsd;
    }

    @Override
    public String toString() {

        return "TransactionEntry{" +
                "blockId=" + blockId +
                ", hash='" + hash + '\'' +
                ", time='" + time + '\'' +
                ", size=" + size +
                ", weight=" + weight +
                ", isCoinbase=" + isCoinbase +
                ", hasWitness=" + hasWitness +
                ", inputTotal=" + inputTotal +
                ", inputTotalUsd=" + inputTotalUsd +
                ", outputTotal=" + outputTotal +
                ", outputTotalUsd=" + outputTotalUsd +
                ", fee=" + fee +
                ", feeUsd=" + feeUsd +
                ", feePerKwu=" + feePerKwu +
                ", feePerKwuUsd=" + feePerKwuUsd +
                '}';
    }
}
