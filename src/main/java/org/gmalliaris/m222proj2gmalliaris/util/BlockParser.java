package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.model.BlockEntry;

import java.nio.file.Path;
import java.util.List;

public class BlockParser extends FileParser<BlockEntry> {

    public static final String ID = "id";
    public static final String HASH = "hash";
    public static final String TIME = "time";
    public static final String REWARD = "reward";
    public static final String REWARD_USD = "reward_usd";
    public static final String GUESSED_MINER = "guessed_miner";
    private static final List<String> MANDATORY_FIELDS = List.of(ID, HASH, TIME,
            REWARD, REWARD_USD, GUESSED_MINER);

    public BlockParser(Path directory) {
        super(directory.toFile(), MANDATORY_FIELDS);
    }

    @Override
    protected BlockEntry convertToEntry(String[] splitEntry) {
        var id = Long.parseLong(splitEntry[attributesMap.get(ID)]);
        var hash = splitEntry[attributesMap.get(HASH)];
        var time = splitEntry[attributesMap.get(TIME)];
        var reward = Long.parseLong(splitEntry[attributesMap.get(REWARD)]);
        var rewardUsd = Double.parseDouble(splitEntry[attributesMap.get(REWARD_USD)]);
        var guessedMiner = splitEntry[attributesMap.get(GUESSED_MINER)];
        return new BlockEntry(id, hash, time, reward, rewardUsd, guessedMiner);
    }
}
