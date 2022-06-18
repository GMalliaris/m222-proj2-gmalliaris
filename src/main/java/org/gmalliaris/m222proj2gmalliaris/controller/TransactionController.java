package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.model.TransactionInputRecipientsAndTotalValues;
import org.gmalliaris.m222proj2gmalliaris.service.InputService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final InputService inputService;

    public TransactionController(InputService inputService) {
        this.inputService = inputService;
    }

    @GetMapping("/{transactionHash}/input-recipients")
    public TransactionInputRecipientsAndTotalValues getTransactionInputsAndTotalValuesByHash(
            @PathVariable("transactionHash") String transactionHash){
        return inputService.getTransactionInputsAndTotalValuesByHash(transactionHash);
    }
}
