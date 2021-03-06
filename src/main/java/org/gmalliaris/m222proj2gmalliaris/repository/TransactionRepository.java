package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Transaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends Neo4jRepository<Transaction, Long> {

    @Query("CREATE INDEX ON:Transaction(hash)")
    void createIdIndex();

    Optional<Transaction> findByHash(String hash);

    @Query("MATCH (n:Transaction)<-[:HAS_TRANSACTION]-(b:Block) " +
            "WHERE b.blockId = $blockId " +
            "RETURN n")
    List<Transaction> findTransactionsByBlockId(Long blockId);

    @Query("MATCH (t:Transaction) " +
            "WHERE t.time >= $start AND t.time < $end " +
            "RETURN t " +
            "ORDER BY t.size DESC " +
            "LIMIT 1")
    Transaction findLargestTransaction(@Param("start") long start,
                                       @Param("end") long end);

    @Query("MATCH (o:Output)<-[:HAS_OUTPUT]-(t:Transaction) " +
            "WHERE ID(o) = $outputId AND t.time >= $start AND t.time < $end " +
            "return t")
    List<Transaction> findTransactionsOfOutputInRange(@Param("outputId") Long outputId,
                                                      @Param("start") long rangeStart,
                                                      @Param("end") long rangeEnd);
}
