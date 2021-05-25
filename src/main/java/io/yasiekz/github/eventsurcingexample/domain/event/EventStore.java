package io.yasiekz.github.eventsurcingexample.domain.event;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("rawtypes")
public interface EventStore {

    void save(final Event<?> event, final int version);

    List<Event> getForAggregate(UUID aggregateId);

    List<Event> getForAggregate(UUID aggregateId, int version);

}
