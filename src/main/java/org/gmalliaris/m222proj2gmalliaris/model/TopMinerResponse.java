package org.gmalliaris.m222proj2gmalliaris.model;

public class TopMinerResponse {
    private final long totalReward;
    private final long totlaBlocks;
    private final String topMiner;

    public TopMinerResponse(long totalReward, long totlaBlocks, String topMiner) {
        this.totalReward = totalReward;
        this.totlaBlocks = totlaBlocks;
        this.topMiner = topMiner;
    }

    public long getTotalReward() {
        return totalReward;
    }

    public long getTotlaBlocks() {
        return totlaBlocks;
    }

    public String getTopMiner() {
        return topMiner;
    }
}
