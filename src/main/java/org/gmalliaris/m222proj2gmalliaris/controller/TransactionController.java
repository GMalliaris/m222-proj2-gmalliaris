package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.model.DateTimeRange;
import org.gmalliaris.m222proj2gmalliaris.model.TransactionRecipientsAndTotalValues;
import org.gmalliaris.m222proj2gmalliaris.service.InputService;
import org.gmalliaris.m222proj2gmalliaris.service.OutputService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final InputService inputService;
    private final OutputService outputService;

    public TransactionController(InputService inputService, OutputService outputService) {
        this.inputService = inputService;
        this.outputService = outputService;
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
}
