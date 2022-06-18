package org.gmalliaris.m222proj2gmalliaris.entity;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Node
public class Block {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private Long blockId;
    @Property
    private String hash;
    @Property
    private long time;
    @Property
    private long reward;
    @Property
    private double rewardUsd;
    @Property
    private String guessedMiner;
    @Relationship(type = "HAS_TRANSACTION")
    private Set<Transaction> transactions = new HashSet<>();
    @Relationship(type = "HAS_INPUT")
    private Set<Input> inputs = new HashSet<>();
    @Relationship(type = "HAS_SPENDING_INPUT")
    private Set<Input> spendingInputs = new HashSet<>();
    @Relationship(type = "HAS_OUTPUT")
    private Set<Output> outputs = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getReward() {
        return reward;
    }

    public void setReward(long reward) {
        this.reward = reward;
    }

    public double getRewardUsd() {
        return rewardUsd;
    }

    public void setRewardUsd(double rewardUsd) {
        this.rewardUsd = rewardUsd;
    }

    public String getGuessedMiner() {
        return guessedMiner;
    }

    public void setGuessedMiner(String guessedMiner) {
        this.guessedMiner = guessedMiner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public Set<Input> getInputs() {
        return inputs;
    }

    public void addInput(Input input) {
        this.inputs.add(input);
    }

    public Set<Input> getSpendingInputs() {
        return spendingInputs;
    }

    public void addSpendingInput(Input spendingInput) {
        this.spendingInputs.add(spendingInput);
    }

    public Set<Output> getOutputs() {
        return outputs;
    }

    public void addOutput(Output output) {
        this.outputs.add(output);
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setInputs(Set<Input> inputs) {
        this.inputs = inputs;
    }

    public void setSpendingInputs(Set<Input> spendingInputs) {
        this.spendingInputs = spendingInputs;
    }

    public void setOutputs(Set<Output> outputs) {
        this.outputs = outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block that = (Block) o;
        return this.blockId.equals(that.blockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId);
    }
}
