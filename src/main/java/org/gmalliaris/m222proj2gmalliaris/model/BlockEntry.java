package org.gmalliaris.m222proj2gmalliaris.model;

public class BlockEntry {

    private final long id;
    private final String hash;
    private final String time;
    private final long reward;
    private final double rewardUsd;
    private final String guessedMiner;

    public BlockEntry(long id, String hash, String time, long reward, double rewardUsd, String guessedMiner) {
        this.id = id;
        this.hash = hash;
        this.time = time;
        this.reward = reward;
        this.rewardUsd = rewardUsd;
        this.guessedMiner = guessedMiner;
    }

    @Override
    public String toString() {
        return "BlockEntry{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", time='" + time + '\'' +
                ", reward=" + reward +
                ", rewardUsd=" + rewardUsd +
                ", guessedMiner='" + guessedMiner + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getTime() {
        return time;
    }

    public long getReward() {
        return reward;
    }

    public double getRewardUsd() {
        return rewardUsd;
    }

    public String getGuessedMiner() {
        return guessedMiner;
    }
}
