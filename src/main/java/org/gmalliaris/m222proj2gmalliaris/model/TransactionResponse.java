package org.gmalliaris.m222proj2gmalliaris.model;

public class TransactionResponse {

    private final String hash;
    private final long time;
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
    private final double feePreKwuUsd;

    public TransactionResponse(String hash, long time, long size, long weight, boolean isCoinbase, boolean hasWitness, long inputTotal, double inputTotalUsd, long outputTotal, double outputTotalUsd, long fee, double feeUsd, double feePerKwu, double feePreKwuUsd) {
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
        this.feePreKwuUsd = feePreKwuUsd;
    }

    public String getHash() {
        return hash;
    }

    public long getTime() {
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

    public double getFeePreKwuUsd() {
        return feePreKwuUsd;
    }
}
