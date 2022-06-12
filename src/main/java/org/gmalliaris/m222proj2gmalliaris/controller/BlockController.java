package org.gmalliaris.m222proj2gmalliaris.controller;

import org.gmalliaris.m222proj2gmalliaris.entity.Block;
import org.gmalliaris.m222proj2gmalliaris.repository.BlockRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

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

    @PostMapping
    private Block save(){
        var block = new Block();
        var id = new Random().nextLong();
        System.out.println(id);
        block.setId(id);
        block.setHash(Long.toHexString(new Random().nextLong()));
        return blockRepository.save(block);
    }

}
