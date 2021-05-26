package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public abstract class SnapshotRepository<T extends EventSourcedAggregate> implements SnapshotLoader<T>,
    SnapshotSaver<T> {

    private final SnapshotMongoRepository<T> snapshotMongoRepository;

    @Override
    public Snapshot<T> load(final UUID aggregateId) {
        log.info("Loading snapshot for aggregate {}", aggregateId);
        return snapshotMongoRepository.findTopByAggregateId(aggregateId);
    }

    @Override
    public void save(final T aggregate) {
        final Snapshot<T> instance = createInstance(aggregate);
        log.info("Saving snapshot for aggregate {}", aggregate);
        snapshotMongoRepository.save(instance);
    }

    protected abstract Snapshot<T> createInstance(final T aggregate);
}
