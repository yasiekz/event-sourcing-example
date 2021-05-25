package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import io.yasiekz.github.eventsurcingexample.domain.event.EventDuplicatedException;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store.EventMongoRepository;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store.MongoEventStore;
import io.yasiekz.github.eventsurcingexample.initializer.MongoFromDockerInitializer;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = MongoFromDockerInitializer.class)
@SpringBootTest
class MongoEventStoreTest {

    private static final UUID AGGREGATE_ID = UUID.randomUUID();
    private static final UUID EVENT1_ID = UUID.randomUUID();
    private static final UUID EVENT2_ID = UUID.randomUUID();
    private static final UUID EVENT3_ID = UUID.randomUUID();

    @Autowired
    private MongoEventStore eventStore;

    @Autowired
    private EventMongoRepository eventMongoRepository;

    @AfterEach
    void tearDown() {
        eventMongoRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save and load event for aggregate")
    void shouldSaveEvent_WhenProvideCorrectData() {
        // given
        FakeEvent event = new FakeEvent(EVENT1_ID, AGGREGATE_ID, "desc");

        // when
        eventStore.save(event, 1);

        // then
        final List<Event> result = eventStore.getForAggregate(AGGREGATE_ID);
        assertEquals(1, result.size());
        assertEquals(event, result.get(0));

    }

    @Test
    @DisplayName("Should save and load events for aggregate from given version")
    void shouldSaveAndLoadEvent_WhenProvideCorrectData() {
        // given
        FakeEvent event1 = new FakeEvent(EVENT1_ID, AGGREGATE_ID, "desc1");
        FakeEvent event2 = new FakeEvent(EVENT2_ID, AGGREGATE_ID, "desc2");
        FakeEvent event3 = new FakeEvent(EVENT3_ID, AGGREGATE_ID, "desc3");

        // when
        eventStore.save(event1, 1);
        eventStore.save(event2, 2);
        eventStore.save(event3, 3);

        // then
        final List<Event> result = eventStore.getForAggregate(AGGREGATE_ID, 1);
        assertEquals(2, result.size());
        assertEquals(event2, result.get(0));
        assertEquals(event3, result.get(1));
    }

    @Test
    @DisplayName("Should throw exception when trying to save 2 identical events")
    void shouldThrowException_WhenTryingToSave2IdenticalEvents() {
        // given
        FakeEvent event = new FakeEvent(EVENT1_ID, AGGREGATE_ID, "desc");
        eventStore.save(event, 1);

        // when
        Executable executable = () -> eventStore.save(event, 2);

        // then
        assertThrows(EventDuplicatedException.class, executable);
    }

    @Test
    @DisplayName("Should throw exception when trying to save 2 events with same version")
    void shouldThrowException_WhenTryingToSave2EventsWithSameVersion() {
        // given
        FakeEvent event1 = new FakeEvent(EVENT1_ID, AGGREGATE_ID, "desc");
        FakeEvent event2 = new FakeEvent(EVENT2_ID, AGGREGATE_ID, "desc");
        eventStore.save(event1, 1);

        // when
        Executable executable = () -> eventStore.save(event2, 1);

        // then
        assertThrows(EventDuplicatedException.class, executable);
    }
}
