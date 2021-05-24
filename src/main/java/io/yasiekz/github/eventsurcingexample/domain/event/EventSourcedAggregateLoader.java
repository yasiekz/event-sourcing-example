package io.yasiekz.github.eventsurcingexample.domain.event;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.util.UUID;

public interface EventSourcedAggregateLoader<T extends EventSourcedAggregate<?>> {

    T load(final UUID aggregateId);

}
