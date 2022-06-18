package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Input;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface InputRepository extends Neo4jRepository<Input, Long> {
}
