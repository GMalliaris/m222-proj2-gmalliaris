package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.model.*;
import org.gmalliaris.m222proj2gmalliaris.repository.OutputRepository;
import org.gmalliaris.m222proj2gmalliaris.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
public class OutputService {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(DATE_TIME_PATTERN);
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

    private final OutputRepository outputRepository;
    private final TransactionRepository transactionRepository;

    public OutputService(OutputRepository repository, TransactionRepository transactionRepository) {
        this.outputRepository = repository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionRecipientsAndTotalValues getTransactionOutputsAndTotalValuesByHash(DateTimeRange dateTimeRange, String transactionHash) throws ParseException {

        var startDate = dateTimeFormatter.parse(dateTimeRange.getRangeStart());
        var endDate = dateTimeFormatter.parse(dateTimeRange.getRangeEnd());

        var outputs = outputRepository.getTransactionInRangeInputs(transactionHash,
                startDate.toInstant().toEpochMilli(),
                endDate.toInstant().toEpochMilli());
        var recipients = new LinkedList<String>();
        long totalValue = 0;
        double totalValueUsd = 0.0;
        for (var output : outputs){
            recipients.add(output.getRecipient());
            totalValue += output.getValue();
            totalValueUsd += output.getValueUsd();
        }
        return new TransactionRecipientsAndTotalValues(recipients, totalValue, totalValueUsd);
    }

    public OutputWithMostTransactionsResponse getOutputWithMostTransactions(SpecificDate specificDate) throws ParseException {

        var date = dateFormatter.parse(specificDate.getDate());
//        var start = date.toInstant().plus(3, ChronoUnit.HOURS); // from central

        var start = date.toInstant();
        var end = start.plus(1, ChronoUnit.DAYS);
        var outputs = outputRepository.getOutputWithMostTransactionInDateTimeRange(start.toEpochMilli(), end.toEpochMilli());
        if (outputs.isEmpty()){
            return null;
        }

        var outputWithMostTransactions = outputs.get(0);
        var transactionsInRange = transactionRepository.findTransactionsOfOutputInRange(outputWithMostTransactions.getId(),
                start.toEpochMilli(), end.toEpochMilli());
        var transactionsCount = transactionsInRange.size();

        var outputResponse = new OutputResponse(outputWithMostTransactions.getTime(),
                outputWithMostTransactions.getValue(), outputWithMostTransactions.getValueUsd(),
                outputWithMostTransactions.getRecipient(), outputWithMostTransactions.getType(),
                outputWithMostTransactions.getSpendable());
        var transactionsResponses = transactionsInRange.stream()
                .map(tr -> new TransactionResponse(tr.getHash(), tr.getTime(), tr.getSize(), tr.getWeight(),
                        tr.isCoinbase(), tr.isHasWitness(), tr.getInputTotal(), tr.getInputTotalUsd(),
                        tr.getOutputTotal(), tr.getInputTotalUsd(), tr.getFee(), tr.getFeeUsd(),
                        tr.getFeePerKwu(), tr.getFeePreKwuUsd()))
                .collect(Collectors.toList());

        return new OutputWithMostTransactionsResponse(outputResponse, transactionsResponses, transactionsCount);
    }
}
