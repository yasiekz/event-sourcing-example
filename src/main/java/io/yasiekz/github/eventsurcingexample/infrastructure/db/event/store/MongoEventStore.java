package io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import io.yasiekz.github.eventsurcingexample.domain.event.EventDuplicatedException;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot.EventToSnapshotGenerationReader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
@Slf4j
@Component
public class MongoEventStore implements EventStore, EventToSnapshotGenerationReader {

    private static final String SORT_BY_FIELD = "_id";
    private static final int PAGE_SIZE = 50;

    private final EventMongoRepository repository;
    private final EventWrapperFactory eventWrapperFactory;

    @Override
    public void save(final Event<?> event, final int version) {

        try {
            final EventWrapper wrapper = eventWrapperFactory.create(event, version);
            log.info("Saving event {} for aggregate with ID={} with hash={}", event,
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
            .peek(e -> log.info("Loading event {} for aggregate {}", e, aggregateId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Event> getForAggregate(final UUID aggregateId, final int version) {
        return repository.findByAggregateId(aggregateId, version)
            .map(EventWrapper::getEvent)
            .peek(e -> log.info("Loading event {} for aggregate {}", e, aggregateId))
            .collect(Collectors.toList());
    }

    @Override
    public Stream<EventWrapper> findEventsForChunkAfterObjectId(final String chunkId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(Direction.ASC, SORT_BY_FIELD));

        return repository.findByChunkId(chunkId, pageable);
    }
}
