package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Input;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface InputRepository extends Neo4jRepository<Input, Long> {

    @Query("MATCH (n:Input)<-[r]-(m:Transaction) WHERE m.hash = $hash RETURN n")
    List<Input> getTransactionInputs(String hash);
}
