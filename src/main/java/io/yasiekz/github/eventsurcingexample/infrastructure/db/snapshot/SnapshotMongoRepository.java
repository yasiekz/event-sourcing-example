package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SnapshotMongoRepository<T extends EventSourcedAggregate> extends MongoRepository<Snapshot<T>, Object> {

    Snapshot<T> findTopByAggregateId(UUID aggregateId);

}
