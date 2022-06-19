package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.model.OutputWithMostTransactionsResponse;
import org.gmalliaris.m222proj2gmalliaris.model.SpecificDate;
import org.gmalliaris.m222proj2gmalliaris.service.OutputService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

@RestController
@RequestMapping("/outputs")
public class OutputController {

    private final OutputService outputService;

    public OutputController(OutputService outputService) {
        this.outputService = outputService;
    }

    @GetMapping("/most-transactions")
    public OutputWithMostTransactionsResponse getOutputWithMostTransactions(@RequestBody @NotNull @Valid SpecificDate specificDate) throws ParseException {
        return outputService.getOutputWithMostTransactions(specificDate);
    }
}
