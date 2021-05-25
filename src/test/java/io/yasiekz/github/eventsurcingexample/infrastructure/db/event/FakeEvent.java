package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.time.LocalDateTime;
import java.util.UUID;

public class FakeEvent extends Event {

    private String description;

    protected FakeEvent(final UUID id, final UUID aggregateId, final String description,
        final LocalDateTime occuredAt) {
        super(id, aggregateId, occuredAt);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void apply(final EventSourcedAggregate aggregate) {
        // do nothing
    }
}
