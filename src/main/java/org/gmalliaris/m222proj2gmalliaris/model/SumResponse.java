package org.gmalliaris.m222proj2gmalliaris.model;

public class SumResponse {

    private final double totalValueUsd;

    public SumResponse(double totalValueUsd) {
        this.totalValueUsd = totalValueUsd;
    }

    public double getTotalValueUsd() {
        return totalValueUsd;
    }
}
