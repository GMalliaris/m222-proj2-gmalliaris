package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.model.SpecificDate;
import org.gmalliaris.m222proj2gmalliaris.model.SumResponse;
import org.gmalliaris.m222proj2gmalliaris.service.InputService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

@RestController
@RequestMapping("/inputs")
public class InputController {

    private final InputService inputService;

    public InputController(InputService inputService) {
        this.inputService = inputService;
    }

    @GetMapping("/{recipient}")
    public SumResponse getRecipientInputsForDay(@PathVariable("recipient") String recipient,
                                                @RequestBody @Valid @NotNull SpecificDate specificDate) throws ParseException {
        return inputService.getInputsByRecipientInDay(recipient, specificDate);
    }
}
