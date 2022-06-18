package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.entity.Input;
import org.gmalliaris.m222proj2gmalliaris.model.BlockTransactionsAndValues;
import org.gmalliaris.m222proj2gmalliaris.model.DateTimeRange;
import org.gmalliaris.m222proj2gmalliaris.model.LargestTransaction;
import org.gmalliaris.m222proj2gmalliaris.repository.InputRepository;
import org.gmalliaris.m222proj2gmalliaris.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

    private final TransactionRepository transactionRepository;
    private final InputRepository inputRepository;

    public TransactionService(TransactionRepository transactionRepository, InputRepository inputRepository) {
        this.transactionRepository = transactionRepository;
        this.inputRepository = inputRepository;
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

    public LargestTransaction getLargestTransaction(DateTimeRange dateTimeRange) throws ParseException {

        var startDate = formatter.parse(dateTimeRange.getRangeStart());
        var endDate = formatter.parse(dateTimeRange.getRangeEnd());
        var transaction = transactionRepository.findLargestTransaction(startDate.toInstant().toEpochMilli(),
                endDate.toInstant().toEpochMilli());

        var inputTotal = transaction.getInputTotal();
        var outputTotal = transaction.getOutputTotal();
        var fee = transaction.getFee();
        var timeMillis = transaction.getTime();
        var recipients = inputRepository.getTransactionInputs(transaction.getHash())
                .stream().map(Input::getRecipient)
                .collect(Collectors.toList());
        var date = Date.from(Instant.ofEpochMilli(timeMillis));

        return new LargestTransaction(inputTotal, outputTotal, fee, formatter.format(date), recipients);
    }
}
