package org.gmalliaris.m222proj2gmalliaris.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Node("Transaction")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String hash;
    @Property
    private long time;
    @Property
    private long size;
    @Property
    private long weight;
    @Property
    private boolean isCoinbase;
    @Property
    private boolean hasWitness;
    @Property
    private long inputTotal;
    @Property
    private double inputTotalUsd;
    @Property
    private long outputTotal;
    @Property
    private double outputTotalUsd;
    @Property
    private long fee;
    @Property
    private double feeUsd;
    @Property
    private double feePerKwu;
    @Property
    private double feePreKwuUsd;
    @Relationship(type = "HAS_INPUT")
    private Set<Input> inputs = new HashSet<>();
    @Relationship(type = "HAS_SPENDING_INPUT")
    private Set<Input> spendingInputs = new HashSet<>();
    @Relationship(type = "HAS_OUTPUT")
    private Set<Output> outputs = new HashSet<>();
    @Transient
    private Long blockId;

    public Transaction() {
//        neo4j
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public boolean isCoinbase() {
        return isCoinbase;
    }

    public void setCoinbase(boolean coinbase) {
        isCoinbase = coinbase;
    }

    public boolean isHasWitness() {
        return hasWitness;
    }

    public void setHasWitness(boolean hasWitness) {
        this.hasWitness = hasWitness;
    }

    public long getInputTotal() {
        return inputTotal;
    }

    public void setInputTotal(long inputTotal) {
        this.inputTotal = inputTotal;
    }

    public double getInputTotalUsd() {
        return inputTotalUsd;
    }

    public void setInputTotalUsd(double inputTotalUsd) {
        this.inputTotalUsd = inputTotalUsd;
    }

    public long getOutputTotal() {
        return outputTotal;
    }

    public void setOutputTotal(long outputTotal) {
        this.outputTotal = outputTotal;
    }

    public double getOutputTotalUsd() {
        return outputTotalUsd;
    }

    public void setOutputTotalUsd(double outputTotalUsd) {
        this.outputTotalUsd = outputTotalUsd;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public double getFeeUsd() {
        return feeUsd;
    }

    public void setFeeUsd(double feeUsd) {
        this.feeUsd = feeUsd;
    }

    public double getFeePerKwu() {
        return feePerKwu;
    }

    public void setFeePerKwu(double feePerKwu) {
        this.feePerKwu = feePerKwu;
    }

    public double getFeePreKwuUsd() {
        return feePreKwuUsd;
    }

    public void setFeePreKwuUsd(double feePreKwuUsd) {
        this.feePreKwuUsd = feePreKwuUsd;
    }

    public void addInput(Input input) {
        this.inputs.add(input);
    }

    public Set<Input> getInputs() {
        return inputs;
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

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
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
        Transaction that = (Transaction) o;
        return this.hash.equals(that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
}
