package org.gmalliaris.m222proj2gmalliaris.repository;

import org.gmalliaris.m222proj2gmalliaris.entity.Output;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutputRepository extends Neo4jRepository<Output, Long> {

    @Query("MATCH (n:Output)<-[r]-(m:Transaction) " +
            "WHERE m.hash = $hash AND m.time > $start and m.time < $end " +
            "RETURN n")
    List<Output> getTransactionInRangeInputs(@Param("hash") String transactionHash,
                                             @Param("start") long rangeStart,
                                             @Param("end") long rangeEnd);

    @Query("MATCH (o:Output)<-[r:HAS_OUTPUT]-(t:Transaction) " +
            "WITH collect(t) AS ot, o, t.time AS time " +
            "WHERE time > $start AND time < $end " +
            "RETURN o " +
            "ORDER BY size(ot) DESC " +
            "LIMIT 1")
    List<Output> getOutputWithMostTransactionInDateTimeRange(@Param("start") long rangeStart,
                                                             @Param("end") long rangeEnd);
}
