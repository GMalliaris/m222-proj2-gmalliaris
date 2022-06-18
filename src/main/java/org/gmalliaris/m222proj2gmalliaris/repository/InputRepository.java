package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Input;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface InputRepository extends Neo4jRepository<Input, Long> {

    @Query("MATCH (n:Input)<-[r]-(m:Transaction) WHERE m.hash = $hash RETURN n")
    List<Input> getTransactionInputs(String hash);

    @Query("match (i:Input) " +
            "where i.recipient = $recipient AND i.time > $start and i.time < $end " +
            "return i")
    List<Input> getInputsByRecipientInRange(@Param("recipient") String recipient,
                                          @Param("start") long start,
                                          @Param("end") long end);
}
