package org.gmalliaris.m222proj2gmalliaris.service;

import org.gmalliaris.m222proj2gmalliaris.model.TopMinerResponse;
import org.gmalliaris.m222proj2gmalliaris.repository.BlockRepository;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    private final BlockRepository blockRepository;


    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public TopMinerResponse getTopMiner(){

        var totalReward = blockRepository.getTotalReward();
        var totalCount = blockRepository.count();
        var blocks = blockRepository.getTopMiner();

        assert blocks.size() == 1;

        return new TopMinerResponse(totalReward, totalCount, blocks.get(0).getGuessedMiner());
    }
}
