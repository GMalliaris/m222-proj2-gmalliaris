package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.entity.Block;
import org.gmalliaris.m222proj2gmalliaris.model.BlockTransactionsAndValues;
import org.gmalliaris.m222proj2gmalliaris.model.InputWithBestExchangeRate;
import org.gmalliaris.m222proj2gmalliaris.model.TopMinerResponse;
import org.gmalliaris.m222proj2gmalliaris.repository.BlockRepository;
import org.gmalliaris.m222proj2gmalliaris.service.BlockService;
import org.gmalliaris.m222proj2gmalliaris.service.InputService;
import org.gmalliaris.m222proj2gmalliaris.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockRepository blockRepository;
    private final TransactionService transactionService;

    private final InputService inputService;
    private final BlockService blockService;

    public BlockController(BlockRepository blockRepository, TransactionService transactionService, InputService inputService, BlockService blockService) {
        this.blockRepository = blockRepository;
        this.transactionService = transactionService;
        this.inputService = inputService;
        this.blockService = blockService;
    }

    @GetMapping
    private List<Block> getAll(){
        return blockRepository.findAll();
    }

    @GetMapping("/{blockId}/transactions")
    public BlockTransactionsAndValues getBlockTransactionsAndValues(@PathVariable("blockId") Long blockId){
        return transactionService.getBlockTransactionsAndValues(blockId);
    }

    @GetMapping("/top-miner-reward")
    public TopMinerResponse getTopMiner(){
        return blockService.getTopMiner();
    }

    @GetMapping("/{blockId}/top-miner-exchange")
    public InputWithBestExchangeRate getInputWithBestExchangeRate(@PathVariable("blockId") Long blockId){
        return inputService.getInputWithBestExchangeRate(blockId);
    }
}
