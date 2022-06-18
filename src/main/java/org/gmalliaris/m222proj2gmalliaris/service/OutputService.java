package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.model.DateTimeRange;
import org.gmalliaris.m222proj2gmalliaris.model.TransactionRecipientsAndTotalValues;
import org.gmalliaris.m222proj2gmalliaris.repository.OutputRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

@Service
public class OutputService {

    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

    private final OutputRepository outputRepository;

    public OutputService(OutputRepository repository) {
        this.outputRepository = repository;
    }

    public TransactionRecipientsAndTotalValues getTransactionInputsAndTotalValuesByHash(DateTimeRange dateTimeRange, String transactionHash) throws ParseException {

        var startDate = formatter.parse(dateTimeRange.getRangeStart());
        var endDate = formatter.parse(dateTimeRange.getRangeEnd());

        var inputs = outputRepository.getTransactionInRangeInputs(transactionHash,
                startDate.toInstant().toEpochMilli(),
                endDate.toInstant().toEpochMilli());
        var recipients = new LinkedList<String>();
        long totalValue = 0;
        double totalValueUsd = 0.0;
        for (var input : inputs){
            recipients.add(input.getRecipient());
            totalValue += input.getValue();
            totalValueUsd += input.getValueUsd();
        }
        return new TransactionRecipientsAndTotalValues(recipients, totalValue, totalValueUsd);
    }
}
