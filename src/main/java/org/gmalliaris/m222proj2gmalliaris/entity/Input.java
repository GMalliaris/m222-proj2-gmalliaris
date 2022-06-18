package org.gmalliaris.m222proj2gmalliaris.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.Objects;

@Node("Input")
public class Input {

    @Id
    @GeneratedValue
    private Long id;
    @Property
    private long time;
    @Property
    private long value;
    @Property
    private double valueUsd;
    @Property
    private String recipient;
    @Property
    private String type;
    @Property
    private Boolean isSpendable;
    @Property
    private long spendingTime;
    @Property
    private double spendingValueUsd;
    @Property
    private long lifespan;
    @Transient
    private Long blockId;
    @Transient
    private String transactionHash;
    @Transient
    private Long spendingBlockId;
    @Transient
    private String spendingTransactionHash;

    public Input() {
//        neo4j
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public double getValueUsd() {
        return valueUsd;
    }

    public void setValueUsd(double valueUsd) {
        this.valueUsd = valueUsd;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSpendable() {
        return isSpendable;
    }

    public void setSpendable(Boolean spendable) {
        isSpendable = spendable;
    }

    public long getSpendingTime() {
        return spendingTime;
    }

    public void setSpendingTime(long spendingTime) {
        this.spendingTime = spendingTime;
    }

    public double getSpendingValueUsd() {
        return spendingValueUsd;
    }

    public void setSpendingValueUsd(double spendingValueUsd) {
        this.spendingValueUsd = spendingValueUsd;
    }

    public long getLifespan() {
        return lifespan;
    }

    public void setLifespan(long lifespan) {
        this.lifespan = lifespan;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Long getSpendingBlockId() {
        return spendingBlockId;
    }

    public void setSpendingBlockId(Long spendingBlockId) {
        this.spendingBlockId = spendingBlockId;
    }

    public String getSpendingTransactionHash() {
        return spendingTransactionHash;
    }

    public void setSpendingTransactionHash(String spendingTransactionHash) {
        this.spendingTransactionHash = spendingTransactionHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input that = (Input) o;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
