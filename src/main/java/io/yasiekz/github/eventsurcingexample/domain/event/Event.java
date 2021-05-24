package io.yasiekz.github.eventsurcingexample.domain.event;

import java.util.UUID;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"id"})
public abstract class Event {

    private final UUID id;

    protected Event(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
