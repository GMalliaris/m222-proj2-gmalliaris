package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.model.TransactionEntry;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class TransactionsParser extends FileParser<TransactionEntry> {

    public static final String BLOCK_ID = "block_id";
    public static final String HASH = "hash";
    public static final String TIME = "time";
    public static final String SIZE = "size";
    public static final String WEIGHT = "weight";
    public static final String IS_COINBASE = "is_coinbase";
    public static final String HAS_WITNESS = "has_witness";
    public static final String INPUT_TOTAL = "input_total";
    public static final String INPUT_TOTAL_USD = "input_total_usd";
    public static final String OUTPUT_TOTAL = "output_total";
    public static final String OUTPUT_TOTAL_USD = "output_total_usd";
    public static final String FEE = "fee";
    public static final String FEE_USD = "fee_usd";
    public static final String FEE_PER_KWU = "fee_per_kwu";
    public static final String FEE_PER_KWU_USD = "fee_per_kwu_usd";
    private static final List<String> MANDATORY_FIELDS = List.of(BLOCK_ID, HASH, TIME,
            SIZE, WEIGHT, IS_COINBASE, HAS_WITNESS, INPUT_TOTAL, INPUT_TOTAL_USD,
            OUTPUT_TOTAL, OUTPUT_TOTAL_USD, FEE, FEE_USD, FEE_PER_KWU, FEE_PER_KWU_USD);

    private final Set<Long> blocksIds;

    public TransactionsParser(Path directory, Set<Long> blocksIds) {
        super(directory.toFile(), MANDATORY_FIELDS);
        this.blocksIds = blocksIds;
    }

    @Override
    protected boolean filter(String[] split) {
        var blockId = split[attributesMap.get(BLOCK_ID)];
        return blocksIds.contains(Long.parseLong(blockId));
    }

    @Override
    public TransactionEntry convertToEntry(String[] splitEntry) {
        var blockId = Long.parseLong(splitEntry[attributesMap.get(BLOCK_ID)]);
        var hash = splitEntry[attributesMap.get(HASH)];
        var time = splitEntry[attributesMap.get(TIME)];
        var size = Long.parseLong(splitEntry[attributesMap.get(SIZE)]);
        var weight = Long.parseLong(splitEntry[attributesMap.get(WEIGHT)]);
        var isCoinbase = "1".equals(splitEntry[attributesMap.get(IS_COINBASE)]);
        var hasWitness = "1".equals(splitEntry[attributesMap.get(HAS_WITNESS)]);
        var inputTotal = Long.parseLong(splitEntry[attributesMap.get(INPUT_TOTAL)]);
        var inputTotalUsd = Double.parseDouble(splitEntry[attributesMap.get(INPUT_TOTAL_USD)]);
        var outputTotal = Long.parseLong(splitEntry[attributesMap.get(OUTPUT_TOTAL)]);
        var outputTotalUsd = Double.parseDouble(splitEntry[attributesMap.get(OUTPUT_TOTAL_USD)]);
        var fee = Long.parseLong(splitEntry[attributesMap.get(FEE)]);
        var feeUsd = Double.parseDouble(splitEntry[attributesMap.get(FEE_USD)]);
        var feePerKwu = Double.parseDouble(splitEntry[attributesMap.get(FEE_PER_KWU)]);
        var feePerKwuUsd = Double.parseDouble(splitEntry[attributesMap.get(FEE_PER_KWU_USD)]);
        return new TransactionEntry(blockId, hash, time, size, weight, isCoinbase, hasWitness,
                inputTotal, inputTotalUsd, outputTotal, outputTotalUsd, fee, feeUsd,
                feePerKwu, feePerKwuUsd);
    }
}
