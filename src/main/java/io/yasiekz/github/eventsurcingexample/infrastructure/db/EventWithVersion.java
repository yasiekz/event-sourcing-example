package io.yasiekz.github.eventsurcingexample.infrastructure.db;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class EventWithVersion<T extends Event> {

    T event;
    int version;

}
