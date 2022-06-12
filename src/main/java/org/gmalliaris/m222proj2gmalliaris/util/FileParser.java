package org.gmalliaris.m222proj2gmalliaris.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class FileParser<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    private final File directory;
    private final List<String> mandatoryFields;

    protected HashMap<String, Integer> attributesMap;

    public FileParser(File directory, List<String> mandatoryFields) {
        this.directory = directory;
        this.mandatoryFields = mandatoryFields;
    }

    private void createAttributesMap(Path randomTransactionsFile) throws IOException {

        Optional<String> attributesLine;
        try(var lineStream = Files.lines(randomTransactionsFile)){
            attributesLine = lineStream
                    .limit(1)
                    .findAny();
        }
        assert attributesLine.isPresent();

        var attributes = attributesLine.get().split("\t");
        attributesMap = new HashMap<>();
        for (var i = 0; i < attributes.length; i++){
            attributesMap.put(attributes[i], i);
        }

        for (var field : mandatoryFields){
            assert attributesMap.containsKey(field);
        }
    }

    public List<T> parseTransactionFiles() throws IOException {

        var tsvFiles = directory.listFiles(file -> file.getName().endsWith(".tsv"));
        createAttributesMap(tsvFiles[0].toPath());

        var entries = new LinkedList<T>();
        for (var file : tsvFiles){
            LOGGER.info("Reading file: {}", file.getName());
            try(var lineStream = Files.lines(file.toPath())){
                var entriesFromFile = lineStream
                        .skip(1)
                        .map(line -> line.split("\t"))
                        .filter(this::filter)
                        .map(this::convertToEntry)
                        .collect(Collectors.toList());
                entries.addAll(entriesFromFile);
            }
        }
        return entries;
    }

    protected boolean filter(String[] split) {
        return true;
    }

    protected abstract T convertToEntry(String[] splitEntry);
}
