package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import io.yasiekz.github.eventsurcingexample.domain.event.EventDuplicatedException;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class MongoEventStore implements EventStore {

    private final EventMongoRepository repository;
    private final EventWrapperFactory eventWrapperFactory;

    @Override
    public void save(final Event event, final int version) {

        try {
            final EventWrapper wrapper = eventWrapperFactory.create(event, version);
            log.info("Saving event with ID={} for aggregate with ID={} with hash={}", event.getId(),
                event.getAggregateId(), wrapper.getEventHash());
            repository.save(wrapper);
        } catch (final DuplicateKeyException e) {
            throw new EventDuplicatedException("Couldn't save same event more than once", e);
        }
    }

    @Override
    public List<Event> getForAggregate(final UUID aggregateId) {
        return repository.findByAggregateId(aggregateId)
            .map(EventWrapper::getEvent)
            .collect(Collectors.toList());
    }

    @Override
    public List<Event> getForAggregate(final UUID aggregateId, final int version) {
        return repository.findByAggregateId(aggregateId, version)
            .map(EventWrapper::getEvent)
            .collect(Collectors.toList());
    }
}
