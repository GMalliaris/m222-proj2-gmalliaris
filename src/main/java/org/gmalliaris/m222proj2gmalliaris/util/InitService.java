package org.gmalliaris.m222proj2gmalliaris.util;

import org.gmalliaris.m222proj2gmalliaris.model.BlockEntry;
import org.gmalliaris.m222proj2gmalliaris.model.TransactionEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
//        readDirectory(Paths.get(args[1], BLOCKS_INPUT_SUB_DIR));
//        readDirectory(Paths.get(args[1], TRANSACTIONS_INPUT_SUB_DIR));

        List<BlockEntry> blocks;
        try {
            blocks = new BlockParser(Paths.get(args[1], BLOCKS_INPUT_SUB_DIR)).parseTransactionFiles();
            LOGGER.info("{} Blocks", blocks.size());
        } catch (IOException e) {
            LOGGER.error("Failed to read transactions");
            blocks = new LinkedList<>();
        }

        try {
            var blocksIds = blocks.stream().map(BlockEntry::getId).collect(Collectors.toList());
            var transactions = new TransactionsParser(Paths.get(args[1], TRANSACTIONS_INPUT_SUB_DIR), blocksIds).parseTransactionFiles();
            LOGGER.info("{} Transactions", transactions.size());

            var usedBlocksIds = transactions.stream()
                    .map(TransactionEntry::getBlockId)
                    .collect(Collectors.toSet());
            var usedBlocks = blocks.stream()
                    .filter(blockEntry -> usedBlocksIds.contains(blockEntry.getId()))
                    .collect(Collectors.toList());
            LOGGER.info("{} used blocks", usedBlocks.size());

        } catch (IOException e) {
            LOGGER.error("Failed to read transactions");
        }

//        readDirectory(Paths.get(args[1], INPUTS_INPUT_SUB_DIR));
//        readDirectory(Paths.get(args[1], OUTPUTS_INPUT_SUB_DIR));
    }

    private void readDirectory(Path path) {

        var pathFile = path.toFile();
        var tsvFiles = pathFile.listFiles((dir, name) -> name.endsWith(".tsv"));

        if (tsvFiles == null || tsvFiles.length == 0){
            invalidArgument(String.format("No .tsv files found in directory '%s'", path));
        }

        LOGGER.info("In directory: {}", path);
        for (var file : tsvFiles){
            try {
                parseFile(file);
            } catch (IOException e) {
                LOGGER.warn("Failed to read file: {}", file.getName());
            }
        }
    }

    private void parseFile(File file) throws IOException {
        var attributesMap = new HashMap<String, Integer>();
        LOGGER.info("\tFile: {}", file.getName());
        Files.lines(file.toPath())
                .limit(1)
                .forEach(line -> {
                    var attributes = line.split("\t");
                    for (int i = 0; i < attributes.length; i++){
                        attributesMap.put(attributes[i], i);
                    }
                });
        LOGGER.info("Attributes: {}", attributesMap);
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
