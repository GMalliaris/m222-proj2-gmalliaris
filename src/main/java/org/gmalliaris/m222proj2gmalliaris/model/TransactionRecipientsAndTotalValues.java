package org.gmalliaris.m222proj2gmalliaris.model;

import java.util.List;

public class TransactionRecipientsAndTotalValues {

    private List<String> recipients;
    private long totalValue;
    private double totalValueUsd;

    public TransactionRecipientsAndTotalValues() {
    }

    public TransactionRecipientsAndTotalValues(List<String> recipients, long totalValue, double totalValueUsd) {
        this.recipients = recipients;
        this.totalValue = totalValue;
        this.totalValueUsd = totalValueUsd;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(long totalValue) {
        this.totalValue = totalValue;
    }

    public double getTotalValueUsd() {
        return totalValueUsd;
    }

    public void setTotalValueUsd(double totalValueUsd) {
        this.totalValueUsd = totalValueUsd;
    }
}
