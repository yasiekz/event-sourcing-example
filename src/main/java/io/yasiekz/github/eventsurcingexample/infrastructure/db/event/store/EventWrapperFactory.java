package io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventWrapperFactory {

    private final EventHashFactory eventHashFactory;

    public EventWrapper create(final Event event, final int version) {

        return EventWrapper.builder()
            .withEvent(event)
            .withEventHash(eventHashFactory.create(event.getId(), event.getAggregateId(), version))
            .withVersion(version)
            .withChunkId(event.getAggregateId().toString().substring(0, 1))
            .build();
    }
}
