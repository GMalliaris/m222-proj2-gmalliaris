package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.model.DateTimeRange;
import org.gmalliaris.m222proj2gmalliaris.model.LargestTransaction;
import org.gmalliaris.m222proj2gmalliaris.model.TransactionRecipientsAndTotalValues;
import org.gmalliaris.m222proj2gmalliaris.service.InputService;
import org.gmalliaris.m222proj2gmalliaris.service.OutputService;
import org.gmalliaris.m222proj2gmalliaris.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final InputService inputService;
    private final OutputService outputService;
    private final TransactionService transactionService;

    public TransactionController(InputService inputService, OutputService outputService, TransactionService transactionService) {
        this.inputService = inputService;
        this.outputService = outputService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionHash}/input-recipients")
    public TransactionRecipientsAndTotalValues getTransactionInputsAndTotalValuesByHash(
            @PathVariable("transactionHash") String transactionHash){
        return inputService.getTransactionInputsAndTotalValuesByHash(transactionHash);
    }

    @GetMapping("/{transactionHash}/output-recipients")
    public TransactionRecipientsAndTotalValues getTransactionOutputsAndTotalValuesByHashInDates(
            @PathVariable("transactionHash") String transactionHash,
            @RequestBody @Valid @NotNull DateTimeRange dateTimeRange) throws ParseException {
        return outputService.getTransactionInputsAndTotalValuesByHash(dateTimeRange, transactionHash);
    }

    @GetMapping("/largest")
    public LargestTransaction getLargestTransaction(@RequestBody @Valid @NotNull DateTimeRange dateTimeRange)
            throws ParseException {
        return transactionService.getLargestTransaction(dateTimeRange);
    }
}
