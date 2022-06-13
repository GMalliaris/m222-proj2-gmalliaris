package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Block;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BlockRepository extends Neo4jRepository<Block, Long> {
}