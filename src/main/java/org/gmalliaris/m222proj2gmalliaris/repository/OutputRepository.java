package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Output;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface OutputRepository extends Neo4jRepository<Output, Long> {
}
