package org.gmalliaris.m222proj2gmalliaris.model;

import java.util.List;

public class LargestTransaction {

    private final long inputTotal;
    private final long outputTotal;
    private final long fee;
    private final String time;
    private final List<String> recipients;

    public LargestTransaction(long inputTotal, long outputTotal, long fee, String time, List<String> recipients) {
        this.inputTotal = inputTotal;
        this.outputTotal = outputTotal;
        this.fee = fee;
        this.time = time;
        this.recipients = recipients;
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

    public String getTime() {
        return time;
    }

    public List<String> getRecipients() {
        return recipients;
    }
}
