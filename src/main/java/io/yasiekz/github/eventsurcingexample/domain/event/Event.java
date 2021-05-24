package io.yasiekz.github.eventsurcingexample.domain.event;

import java.util.UUID;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"id"})
public abstract class Event {

    private final UUID id;
    private final UUID aggregateId;

    protected Event(final UUID id, final UUID aggregateId) {
        this.id = id;
        this.aggregateId = aggregateId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }
}
