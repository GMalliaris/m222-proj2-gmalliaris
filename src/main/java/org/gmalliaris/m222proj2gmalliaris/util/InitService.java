package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.entity.Block;
import org.gmalliaris.m222proj2gmalliaris.entity.Input;
import org.gmalliaris.m222proj2gmalliaris.entity.Output;
import org.gmalliaris.m222proj2gmalliaris.entity.Transaction;
import org.gmalliaris.m222proj2gmalliaris.repository.BlockRepository;
import org.gmalliaris.m222proj2gmalliaris.repository.InputRepository;
import org.gmalliaris.m222proj2gmalliaris.repository.OutputRepository;
import org.gmalliaris.m222proj2gmalliaris.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//
//MATCH (n)
//WITH n LIMIT 100000
//DETACH DELETE n
//RETURN count(*);
@Component
@Profile("init")
public class InitService implements CommandLineRunner {

    private final BlockRepository blockRepository;
    private final TransactionRepository transactionRepository;
    private final InputRepository inputRepository;
    private final OutputRepository outputRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitService.class);
    private static final String INPUT_DIR_ARGUMENT = "--inputDir";
    private static final String BLOCKS_INPUT_SUB_DIR = "blocks";
    private static final String TRANSACTIONS_INPUT_SUB_DIR = "transactions";
    private static final String INPUTS_INPUT_SUB_DIR = "inputs";
    private static final String OUTPUTS_INPUT_SUB_DIR = "outputs";
    private String[] args;

    public InitService(BlockRepository blockRepository, TransactionRepository transactionRepository, InputRepository inputRepository, OutputRepository outputRepository) {
        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
        this.inputRepository = inputRepository;
        this.outputRepository = outputRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        this.args = args;
        validateArgs();
        
        if (blockRepository.count() != 0){
            invalidArgument("Cannot migrate data, database is not empty.");
        }

        var blocks = readBlockEntries();
        var blockMap = blocks.stream()
                .collect(Collectors.toMap(Block::getBlockId, block -> block));

        var transactions = readTransactionEntries(blockMap.keySet(), 4000);
        var transactionMap = transactions.stream()
                .collect(Collectors.toMap(Transaction::getHash, tr -> tr));

        readInputEntries(blockMap.keySet(), transactionMap.keySet(), 16000)
            .stream()
            .map(inputRepository::save)
            .peek(input -> {
                var transactionHash = input.getTransactionHash();
                var transaction = transactionMap.get(transactionHash);
                if (transaction == null){
                    throw new IllegalStateException("Input Transaction by hash not found");
                }
                transaction.addInput(input);
            })
            .peek(input -> {
                var spendingTransactionHash = input.getSpendingTransactionHash();
                var spendingTransaction = transactionMap.get(spendingTransactionHash);
                if (spendingTransaction == null){
                    throw new IllegalStateException("Input spending transaction by hash not found");
                }
                spendingTransaction.addSpendingInput(input);
            })
            .peek(input -> {
                var blockId = input.getBlockId();
                var block = blockMap.get(blockId);
                if (block == null){
                    throw new IllegalStateException("Input block by id not found");
                }
                block.addInput(input);
            })
            .forEach(input -> {
                var spendingBlockId = input.getSpendingBlockId();
                var spendingBlock = blockMap.get(spendingBlockId);
                if (spendingBlock == null){
                    throw new IllegalStateException("Input spending block by id not found");
                }
                spendingBlock.addSpendingInput(input);
            });
        LOGGER.info("Persisted inputs");

        readOutputEntries(blockMap.keySet(), transactionMap.keySet(), 8000)
            .stream()
            .map(outputRepository::save)
            .peek(output -> {
                var transactionHash = output.getTransactionHash();
                var transaction = transactionMap.get(transactionHash);
                if (transaction == null){
                    throw new IllegalStateException("Output transaction by hash not found");
                }
                transaction.addOutput(output);
            })
            .forEach(output -> {
                var blockId = output.getBlockId();
                var block = blockMap.get(blockId);
                if (block == null){
                    throw new IllegalStateException("Output block by id not found");
                }
                block.addOutput(output);
            });
        LOGGER.info("Persisted outputs");

        transactions.stream()
            .filter(tr -> !tr.getInputs().isEmpty() ||
                    !tr.getSpendingInputs().isEmpty() ||
                    !tr.getOutputs().isEmpty())
            .peek(transactionRepository::save)
            .forEach(transaction -> {
                var blockId = transaction.getBlockId();
                var block = blockMap.get(blockId);
                if (block == null){
                    throw new IllegalStateException("Transaction block by id not found");
            }
            block.addTransaction(transaction);
        });
        LOGGER.info("Persisted transactions");

        blocks.stream()
            .filter(block -> !block.getTransactions().isEmpty() ||
                !block.getInputs().isEmpty() ||
                !block.getSpendingInputs().isEmpty() ||
                !block.getOutputs().isEmpty())
            .forEach(blockRepository::save);
        LOGGER.info("Persisted blocks");

        LOGGER.info("Initialization done");
    }

    private List<Block> readBlockEntries(){
        return readBlockEntries(0);
    }

    private List<Block> readBlockEntries(int limit){
        try {
            var blocks = new BlockParser(Paths.get(args[1], BLOCKS_INPUT_SUB_DIR)).parseFiles();
            LOGGER.info("{} Blocks", blocks.size());
            if (limit > blocks.size()) {
                LOGGER.info("Limiting to {} Blocks", limit);
            }
            var blockStream = limit > 0
                    ? blocks.stream().limit(limit)
                    : blocks.stream();

            return blockStream
                    .map(BlockParser::entryToNode)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Failed to read transactions");
            return List.of();
        }
    }

    private List<Transaction> readTransactionEntries(Set<Long> blockIds) {
        return readTransactionEntries(blockIds, 0);
    }

    private List<Transaction> readTransactionEntries(Set<Long> blockIds, int limit) {
        try {
            var transactions = new TransactionsParser(Paths.get(args[1], TRANSACTIONS_INPUT_SUB_DIR), blockIds).parseFiles();
            LOGGER.info("{} Transactions", transactions.size());
            if (limit > transactions.size()) {
                LOGGER.info("Limiting to {} Transactions", limit);
            }
            var transactionsStream = limit > 0
                    ? transactions.stream().limit(limit)
                    : transactions.stream();
            return transactionsStream
                    .map(TransactionsParser::entryToEntity)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Failed to read transactions");
            return List.of();
        }
    }

    private List<Input> readInputEntries(Set<Long> blockIds, Set<String> transactionHashes){
        return readInputEntries(blockIds, transactionHashes, 0);
    }

    private List<Input> readInputEntries(Set<Long> blockIds, Set<String> transactionHashes, int limit){
        try {
            var inputs = new InputParser(Paths.get(args[1], INPUTS_INPUT_SUB_DIR), blockIds, transactionHashes)
                    .parseFiles();
            LOGGER.info("{} Inputs", inputs.size());
            if (limit > inputs.size()){
                LOGGER.info("Limiting to {} Inputs", limit);
            }
            var inputStream = limit > 0
                    ? inputs.stream().limit(limit)
                    : inputs.stream();
            return inputStream
                    .map(InputParser::entryToEntity)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Failed to read inputs");
            return List.of();
        }
    }

    private List<Output> readOutputEntries(Set<Long> blockIds, Set<String> transactionHashes){
        return readOutputEntries(blockIds, transactionHashes, 0);
    }

    private List<Output> readOutputEntries(Set<Long> blockIds, Set<String> transactionHashes, int limit){
        try {
            var outputs = new OutputParser(Paths.get(args[1], OUTPUTS_INPUT_SUB_DIR), blockIds, transactionHashes)
                    .parseFiles();
            LOGGER.info("{} Outputs", outputs.size());
            if (limit > outputs.size()){
                LOGGER.info("Limiting to {} Outputs", limit);
            }
            var outputStream = limit > 0
                    ? outputs.stream().limit(limit)
                    : outputs.stream();
            return outputStream
                    .map(OutputParser::entryToEntity)
                    .collect(Collectors.toList());
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
