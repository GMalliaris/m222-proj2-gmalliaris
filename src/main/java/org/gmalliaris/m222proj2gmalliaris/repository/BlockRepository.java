package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Block;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends Neo4jRepository<Block, Long> {

    @Query("CREATE INDEX ON:Block(blockId)")
    void createIdIndex();

    Optional<Block> findByBlockId(long blockId);

    @Query("MATCH (b:Block) RETURN sum(b.reward)")
    long getTotalReward();

    @Query("MATCH (b:Block) " +
            "RETURN b " +
            "ORDER BY b.reward " +
            "LIMIT 1")
    List<Block> getTopMiner();
}
