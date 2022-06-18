package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.entity.Output;
import org.gmalliaris.m222proj2gmalliaris.model.OutputEntry;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

public class OutputParser extends FileParser<OutputEntry> {

    private final static String BLOCK_ID = "block_id";
    private final static String TRANSACTION_HASH = "transaction_hash";
    private final static String TIME = "time";
    private final static String VALUE = "value";
    private final static String VALUE_USD = "value_usd";
    private final static String RECIPIENT = "recipient";
    private final static String TYPE = "type";
    private final static String IS_SPENDABLE = "is_spendable";

    private final static List<String> MANDATORY_FIELDS = List.of(BLOCK_ID, TRANSACTION_HASH,
            TIME, VALUE, VALUE_USD, RECIPIENT, TYPE, IS_SPENDABLE);

    private final Set<Long> blockIds;
    private final Set<String> transactionHashes;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public OutputParser(Path directory, Set<Long> blockIds, Set<String> transactionHashes) {
        super(directory.toFile(), MANDATORY_FIELDS);
        this.blockIds = blockIds;
        this.transactionHashes = transactionHashes;
    }

    @Override
    protected boolean filter(String[] split) {
        var blockId = Long.parseLong(split[attributesMap.get(BLOCK_ID)]);
        var transactionHash = split[attributesMap.get(TRANSACTION_HASH)];
        return blockIds.contains(blockId)
                && transactionHashes.contains(transactionHash);
    }

    @Override
    protected OutputEntry convertToEntry(String[] splitEntry) {
        var blockId = Long.parseLong(splitEntry[attributesMap.get(BLOCK_ID)]);
        var transactionHash = splitEntry[attributesMap.get(TRANSACTION_HASH)];
        var time = splitEntry[attributesMap.get(TIME)];
        var value = Long.parseLong(splitEntry[attributesMap.get(VALUE)]);
        var valueUsd = Double.parseDouble(splitEntry[attributesMap.get(VALUE)]);
        var recipient = splitEntry[attributesMap.get(RECIPIENT)];
        var type = splitEntry[attributesMap.get(RECIPIENT)];
        var isSpendableString = splitEntry[attributesMap.get(IS_SPENDABLE)];
        Boolean isSpendable = null;
        if ("1".equals(isSpendableString)){
            isSpendable = true;
        }
        else if("0".equals(isSpendableString)){
            isSpendable = false;
        }
        return new OutputEntry(blockId, transactionHash, time, value, valueUsd, recipient,
                type, isSpendable);
    }

    public static Output entryToEntity(OutputEntry entry){
        var output = new Output();
        try {
            output.setTime(formatter.parse(entry.getTime()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        output.setValue(entry.getValue());
        output.setValueUsd(entry.getValueUsd());
        output.setRecipient(entry.getRecipient());
        output.setType(entry.getType());
        output.setSpendable(entry.getIsSpendable());
        output.setBlockId(entry.getBlockId());
        output.setTransactionHash(entry.getTransactionsHash());
        return output;
    }
}
