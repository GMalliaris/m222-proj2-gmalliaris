package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.model.InputWithBestExchangeRate;
import org.gmalliaris.m222proj2gmalliaris.model.SpecificDate;
import org.gmalliaris.m222proj2gmalliaris.model.SumResponse;
import org.gmalliaris.m222proj2gmalliaris.model.TransactionRecipientsAndTotalValues;
import org.gmalliaris.m222proj2gmalliaris.repository.InputRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

@Service
public class InputService {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

    private final InputRepository inputRepository;

    public InputService(InputRepository inputRepository) {
        this.inputRepository = inputRepository;
    }

    public TransactionRecipientsAndTotalValues getTransactionInputsAndTotalValuesByHash(String transactionHash){

        var inputs = inputRepository.getTransactionInputs(transactionHash);
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

    public SumResponse getInputsByRecipientInDay(String recipient, SpecificDate specificDate) throws ParseException {

        var start = formatter.parse(specificDate.getDate()).toInstant();
        var end = start.plus(1, ChronoUnit.DAYS);
        double totalValueUsd = 0.0;
        var inputs = inputRepository.getInputsByRecipientInRange(recipient, start.toEpochMilli(), end.toEpochMilli());
        for (var input : inputs){
            totalValueUsd += input.getValueUsd();
        }
        return new SumResponse(totalValueUsd);
    }

    public InputWithBestExchangeRate getInputWithBestExchangeRate(Long blockId){

        var inputs = inputRepository.getBlockInputWithBestExchangeRate(blockId);
        assert !inputs.isEmpty();

        var topInput = inputs.get(0);
        return new InputWithBestExchangeRate(topInput.getRecipient(), topInput.getValue(), topInput.getValueUsd());
    }
}
