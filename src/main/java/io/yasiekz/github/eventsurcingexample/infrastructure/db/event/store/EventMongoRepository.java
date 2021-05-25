package io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store;

import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EventMongoRepository extends MongoRepository<EventWrapper, UUID> {

    Stream<EventWrapper> findByAggregateId(UUID aggregateId);

    @Query("{'aggregateId': ?0, 'version': {$gt: ?1} }")
    Stream<EventWrapper> findByAggregateId(UUID aggregateId, int version);

}
