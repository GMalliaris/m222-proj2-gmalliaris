package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.entity.Block;
import org.gmalliaris.m222proj2gmalliaris.repository.BlockRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockRepository blockRepository;

    public BlockController(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @GetMapping
    private List<Block> getAll(){
        return blockRepository.findAll();
    }

}
