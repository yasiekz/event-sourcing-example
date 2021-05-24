package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.util.UUID;

public class FakeEvent extends Event {

    private String description;

    protected FakeEvent(final UUID id, final UUID aggregateId, final String description) {
        super(id, aggregateId);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
