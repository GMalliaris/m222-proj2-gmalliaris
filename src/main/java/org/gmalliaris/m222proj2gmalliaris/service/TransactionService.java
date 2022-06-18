package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.model.BlockTransactionsAndValues;
import org.gmalliaris.m222proj2gmalliaris.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BlockTransactionsAndValues getBlockTransactionsAndValues(Long blockId){

        var transactions = transactionRepository.findTransactionsByBlockId(blockId);
        long count = transactions.size();
        long inputTotal = 0;
        long outputTotal = 0;
        long fee = 0;
        for (var transaction : transactions){
            inputTotal += transaction.getInputTotal();
            outputTotal += transaction.getOutputTotal();
            fee += transaction.getFee();
        }
        return new BlockTransactionsAndValues(count, inputTotal, outputTotal, fee);
    }
}
