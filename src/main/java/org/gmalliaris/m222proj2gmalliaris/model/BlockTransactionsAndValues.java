package org.gmalliaris.m222proj2gmalliaris.model;

public class BlockTransactionsAndValues {

    private final long count;
    private final long inputTotal;
    private final long outputTotal;
    private final long fee;

    public BlockTransactionsAndValues(long count, long inputTotal, long outputTotal, long fee) {
        this.count = count;
        this.inputTotal = inputTotal;
        this.outputTotal = outputTotal;
        this.fee = fee;
    }

    public long getCount() {
        return count;
    }

    public long getInputTotal() {
        return inputTotal;
    }

    public long getOutputTotal() {
        return outputTotal;
    }

    public long getFee() {
        return fee;
    }
}
