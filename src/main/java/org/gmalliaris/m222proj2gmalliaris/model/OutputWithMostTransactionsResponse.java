package org.gmalliaris.m222proj2gmalliaris.model;

import java.util.List;

public class OutputWithMostTransactionsResponse {

    private final OutputResponse output;
    private final List<TransactionResponse> transactions;
    private final long totalTransactionsCount;

    public OutputWithMostTransactionsResponse(OutputResponse output, List<TransactionResponse> transactions, long totalTransactionsCount) {
        this.output = output;
        this.transactions = transactions;
        this.totalTransactionsCount = totalTransactionsCount;
    }

    public OutputResponse getOutput() {
        return output;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public long getTotalTransactionsCount() {
        return totalTransactionsCount;
    }
}
