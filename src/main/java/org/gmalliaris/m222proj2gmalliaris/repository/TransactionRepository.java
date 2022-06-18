package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Transaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface TransactionRepository extends Neo4jRepository<Transaction, Long> {

    @Query("CREATE INDEX ON:Transaction(hash)")
    void createIdIndex();

    Optional<Transaction> findByHash(String hash);
}
