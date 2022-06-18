package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.entity.Input;
import org.gmalliaris.m222proj2gmalliaris.model.InputEntry;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

public class InputParser extends FileParser<InputEntry>{

    private static final String BLOCK_ID = "block_id";
    private static final String TRANSACTION_HASH = "transaction_hash";
    private static final String TIME = "time";
    private static final String VALUE = "value";
    private static final String VALUE_USD = "value_usd";
    private static final String RECIPIENT = "recipient";
    private static final String TYPE = "type";
    private static final String IS_SPENDABLE = "is_spendable";
    private static final String SPENDING_BLOCK_ID = "spending_block_id";
    private static final String SPENDING_TRANSACTION_HASH = "spending_transaction_hash";
    private static final String SPENDING_TIME = "spending_time";
    private static final String SPENDING_VALUE_USD = "spending_value_usd";
    private static final String LIFESPAN = "lifespan";

    private static final List<String> MANDATORY_FIELDS = List.of(BLOCK_ID, TRANSACTION_HASH,
            TIME, VALUE, VALUE_USD, RECIPIENT, TYPE, IS_SPENDABLE, SPENDING_BLOCK_ID,
            SPENDING_TRANSACTION_HASH, SPENDING_TIME, SPENDING_VALUE_USD, LIFESPAN);

    private final Set<Long> blockIds;
    private final Set<String> transactionHashes;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public InputParser(Path directory, Set<Long> blockIds, Set<String> transactionHashes) {
        super(directory.toFile(), MANDATORY_FIELDS);
        this.blockIds = blockIds;
        this.transactionHashes = transactionHashes;
    }

    @Override
    protected boolean filter(String[] split) {
        var blockId = Long.parseLong(split[attributesMap.get(BLOCK_ID)]);
        var spendingBlockId = Long.parseLong(split[attributesMap.get(SPENDING_BLOCK_ID)]);
        var transactionHash = split[attributesMap.get(TRANSACTION_HASH)];
        var spendingTransactionHash = split[attributesMap.get(SPENDING_TRANSACTION_HASH)];
        return blockIds.contains(blockId)
                && blockIds.contains(spendingBlockId)
                && transactionHashes.contains(transactionHash)
                && transactionHashes.contains(spendingTransactionHash);
    }

    @Override
    protected InputEntry convertToEntry(String[] splitEntry) {

        var blockId = Long.parseLong(splitEntry[attributesMap.get(BLOCK_ID)]);
        var transactionHash = splitEntry[attributesMap.get(TRANSACTION_HASH)];
        var time = splitEntry[attributesMap.get(TIME)];
        var value = Long.parseLong(splitEntry[attributesMap.get(VALUE)]);
        var valueUsd = Double.parseDouble(splitEntry[attributesMap.get(VALUE_USD)]);
        var recipient = splitEntry[attributesMap.get(RECIPIENT)];
        var type = splitEntry[attributesMap.get(TYPE)];
        var isSpendableString = splitEntry[attributesMap.get(IS_SPENDABLE)];
        Boolean isSpendable = null;
        if ("1".equals(isSpendableString)){
            isSpendable = true;
        }
        else if ("0".equals(isSpendableString)){
            isSpendable = true;
        }
        var spendingBlockId = Long.parseLong(splitEntry[attributesMap.get(SPENDING_BLOCK_ID)]);
        var spendingTransactionHash = splitEntry[attributesMap.get(SPENDING_TRANSACTION_HASH)];
        var spendingTime = splitEntry[attributesMap.get(SPENDING_TIME)];
        var spendingValueUsd = Double.parseDouble(splitEntry[attributesMap.get(SPENDING_VALUE_USD)]);
        var lifespan = Long.parseLong(splitEntry[attributesMap.get(LIFESPAN)]);
        return new InputEntry(blockId, transactionHash, time, value, valueUsd, recipient, type, isSpendable,
                spendingBlockId, spendingTransactionHash, spendingTime, spendingValueUsd, lifespan);
    }

    public static Input entryToEntity(InputEntry entry){
        var input = new Input();
        try {
            input.setTime(formatter.parse(entry.getTime()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        input.setValue(entry.getValue());
        input.setValueUsd(entry.getValueUsd());
        input.setRecipient(entry.getRecipient());
        input.setType(entry.getType());
        input.setSpendable(entry.getSpendable());
        try {
            input.setSpendingTime(formatter.parse(
                    entry.getSpendingTime()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        input.setSpendingValueUsd(entry.getSpendingValueUsd());
        input.setLifespan(entry.getLifespan());
        input.setBlockId(entry.getBlockId());
        input.setTransactionHash(entry.getTransactionHash());
        input.setSpendingBlockId(entry.getSpendingBlockId());
        input.setSpendingTransactionHash(entry.getSpendingTransactionHash());
        return input;
    }
}
