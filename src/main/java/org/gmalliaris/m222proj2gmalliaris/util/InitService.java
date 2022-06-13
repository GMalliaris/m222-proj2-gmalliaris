package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.model.BlockEntry;
import org.gmalliaris.m222proj2gmalliaris.model.InputEntry;
import org.gmalliaris.m222proj2gmalliaris.model.OutputEntry;
import org.gmalliaris.m222proj2gmalliaris.model.TransactionEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Profile("init")
public class InitService implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitService.class);
    private static final String INPUT_DIR_ARGUMENT = "--inputDir";
    private static final String BLOCKS_INPUT_SUB_DIR = "blocks";
    private static final String TRANSACTIONS_INPUT_SUB_DIR = "transactions";
    private static final String INPUTS_INPUT_SUB_DIR = "inputs";
    private static final String OUTPUTS_INPUT_SUB_DIR = "outputs";
    private String[] args;

    @Override
    public void run(String... args) {
        this.args = args;
        validateArgs();

        var blocks = readBlockEntries();
        var blockIds = blocks.stream()
                .map(BlockEntry::getId)
                .collect(Collectors.toSet());

        var transactions = readTransactionEntries(blockIds);
        var transactionHashes = transactions.stream()
                .map(TransactionEntry::getHash)
                .collect(Collectors.toSet());

        var inputs = readInputEntries(blockIds, transactionHashes);

        var outputs = readOutputEntries(blockIds, transactionHashes);

    }

    private List<BlockEntry> readBlockEntries(){
        try {
            var blocks = new BlockParser(Paths.get(args[1], BLOCKS_INPUT_SUB_DIR)).parseFiles();
            LOGGER.info("{} Blocks", blocks.size());
            return blocks;
        } catch (IOException e) {
            LOGGER.error("Failed to read transactions");
            return List.of();
        }
    }

    private List<TransactionEntry> readTransactionEntries(Set<Long> blockIds) {
        try {
            var transactions = new TransactionsParser(Paths.get(args[1], TRANSACTIONS_INPUT_SUB_DIR), blockIds).parseFiles();
            LOGGER.info("{} Transactions", transactions.size());
            return transactions;
        } catch (IOException e) {
            LOGGER.error("Failed to read transactions");
            return List.of();
        }
    }

    private List<InputEntry> readInputEntries(Set<Long> blockIds, Set<String> transactionHashes){
        try {
            var inputs = new InputParser(Paths.get(args[1], INPUTS_INPUT_SUB_DIR), blockIds, transactionHashes)
                    .parseFiles();
            LOGGER.info("{} Inputs", inputs.size());
            return inputs;
        } catch (IOException e) {
            LOGGER.error("Failed to read inputs");
            return List.of();
        }
    }

    private List<OutputEntry> readOutputEntries(Set<Long> blockIds, Set<String> transactionHashes){
        try {
            var outputs = new OutputParser(Paths.get(args[1], OUTPUTS_INPUT_SUB_DIR), blockIds, transactionHashes)
                    .parseFiles();
            LOGGER.info("{} Outputs", outputs.size());
            return outputs;
        } catch (IOException e) {
            LOGGER.error("Failed to read inputs");
            return List.of();
        }
    }

    private void validateArgs() {
        if (args.length != 2 || !args[0].equals(INPUT_DIR_ARGUMENT)){
            invalidArgument("Required arguments: '--inputDir inputDirectory'");
        }

        if (!Files.isDirectory(Paths.get(args[1]))){
            invalidArgument(String.format("'%s' is not a valid directory", args[1]));
        }

        if (!Files.isDirectory(Paths.get(args[1], BLOCKS_INPUT_SUB_DIR))){
            invalidArgument(String.format("`%s` does not contain `/blocks` directory", args[1]));
        }

        if (!Files.isDirectory(Paths.get(args[1], TRANSACTIONS_INPUT_SUB_DIR))){
            invalidArgument(String.format("`%s` does not contain `/transactions` directory", args[1]));
        }

        if (!Files.isDirectory(Paths.get(args[1], INPUTS_INPUT_SUB_DIR))){
            invalidArgument(String.format("`%s` does not contain `/inputs` directory", args[1]));
        }

        if (!Files.isDirectory(Paths.get(args[1], OUTPUTS_INPUT_SUB_DIR))){
            invalidArgument(String.format("`%s` does not contain `/outputs` directory", args[1]));
        }
    }

    private void invalidArgument(String errMsg){
        LOGGER.error("Invalid arguments: {}", Arrays.asList(args));
        LOGGER.error(errMsg);
        System.exit(-1);
    }
}
