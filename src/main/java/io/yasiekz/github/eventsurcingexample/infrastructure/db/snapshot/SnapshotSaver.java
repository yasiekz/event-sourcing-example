package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;

public interface SnapshotSaver<T extends EventSourcedAggregate> {

    void save(T aggregate);

}
