package io.yasiekz.github.eventsurcingexample.domain.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.stub.FakeAggregate;
import io.yasiekz.github.eventsurcingexample.domain.aggregate.stub.FakeAggregateChanged;
import io.yasiekz.github.eventsurcingexample.domain.aggregate.stub.FakeAggregateCreated;
import io.yasiekz.github.eventsurcingexample.domain.aggregate.stub.FakeEventSourcedRepository;
import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventSourcedRepositoryTest {

    private static final UUID AGGREGATE_ID = UUID.randomUUID();

    @Mock
    private EventStore eventStore;

    private FakeEventSourcedRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FakeEventSourcedRepository(eventStore);
    }

    @Test
    @DisplayName("Should save events for aggregate when aggregate is new")
    void shouldSaveEventsForAggregate_whenAggregateIsNew() {

        // given
        final FakeAggregate fakeAggregate = new FakeAggregate(AGGREGATE_ID);
        fakeAggregate.changeDescription("desc1");
        fakeAggregate.changeDescription("desc2");

        // when
        repository.save(fakeAggregate);

        // then
        verify(eventStore, times(3)).save(any(), anyInt());
    }

    @Test
    @DisplayName("Should save events for aggregate when aggregate is changed")
    void shouldSaveEventsForAggregate_whenAggregateIsChanged() {

        // given
        final List<Event> events = List.of(
            new FakeAggregateCreated(UUID.randomUUID(), AGGREGATE_ID),
            new FakeAggregateChanged(UUID.randomUUID(), AGGREGATE_ID, "desc1")
        );
        when(eventStore.getForAggregate(any())).thenReturn(events);
        final FakeAggregate aggregate = repository.load(AGGREGATE_ID);
        aggregate.changeDescription("desc2");

        // when
        repository.save(aggregate);

        // then
        verify(eventStore, times(1)).save(any(), anyInt());
    }

    @Test
    @DisplayName("Should load aggregate from events")
    void shouldLoadAggregate_whenEventsAreRead() {

        // given
        final List<Event> events = List.of(
            new FakeAggregateCreated(UUID.randomUUID(), AGGREGATE_ID),
            new FakeAggregateChanged(UUID.randomUUID(), AGGREGATE_ID, "desc1"),
            new FakeAggregateChanged(UUID.randomUUID(), AGGREGATE_ID, "desc2")
        );
        when(eventStore.getForAggregate(any())).thenReturn(events);

        // when
        final FakeAggregate result = repository.load(AGGREGATE_ID);

        // then
        verify(eventStore).getForAggregate(eq(AGGREGATE_ID));
        assertEquals("desc2", result.getDescription());
        assertEquals(AGGREGATE_ID, result.getId());
    }

    @Test
    @DisplayName("Should throw exception when no events found")
    void shouldThrowException_whenNoEventsFound() {

        // given
        when(eventStore.getForAggregate(any())).thenReturn(List.of());

        // when
        Executable executable = () -> repository.load(AGGREGATE_ID);

        // then
        assertThrows(AggregateNotFoundException.class, executable);
    }
}
