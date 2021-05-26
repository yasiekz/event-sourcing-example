package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.util.UUID;

public interface SnapshotLoader<T extends EventSourcedAggregate> {

    Snapshot<T> load(final UUID aggregateId);

}
