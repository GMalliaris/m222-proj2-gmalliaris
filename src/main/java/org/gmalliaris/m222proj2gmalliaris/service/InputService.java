package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.model.TransactionRecipientsAndTotalValues;
import org.gmalliaris.m222proj2gmalliaris.repository.InputRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class InputService {

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
}
