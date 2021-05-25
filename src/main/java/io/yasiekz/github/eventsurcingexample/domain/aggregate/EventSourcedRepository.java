package io.yasiekz.github.eventsurcingexample.domain.aggregate;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import io.yasiekz.github.eventsurcingexample.domain.event.EventSourcedAggregateLoader;
import io.yasiekz.github.eventsurcingexample.domain.event.EventSourcedAggregateSaver;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public abstract class EventSourcedRepository<T extends EventSourcedAggregate> implements
    EventSourcedAggregateSaver<T>, EventSourcedAggregateLoader<T> {

    private final EventStore eventStore;

    @Override
    public void save(final T aggregate) {

        final int version = aggregate.getVersion();
        int i = 1;

        for (Event<?> event : aggregate.getRecordedEvents()) {
            eventStore.save(event, version + i);
            i++;
        }
        aggregate.markEventsAsSaved();
    }

    @Override
    public T load(final UUID aggregateId) {

        final List<Event> events = eventStore.getForAggregate(aggregateId);

        if (events.isEmpty()) {
            throw new AggregateNotFoundException(aggregateId);
        }

        T instance = createInstance();
        events.forEach(instance::applyPastEvent);

        return instance;
    }

    protected abstract T createInstance();
}
