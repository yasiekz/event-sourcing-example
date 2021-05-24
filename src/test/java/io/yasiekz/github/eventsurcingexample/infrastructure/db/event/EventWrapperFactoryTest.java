package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventWrapperFactoryTest {

    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final UUID AGGREGATE_ID = UUID.randomUUID();
    private static final String HASH = "hash";
    private static final String DESCRIPTION = "desc";

    @Mock
    private EventHashFactory eventHashFactory;

    private EventWrapperFactory eventWrapperFactory;

    @BeforeEach
    void setUp() {
        eventWrapperFactory = new EventWrapperFactory(eventHashFactory);
    }

    @Test
    @DisplayName("Should create event wrapper")
    void shouldCreateEventWrapper() {

        // given
        when(eventHashFactory.create(eq(EVENT_ID), eq(AGGREGATE_ID), eq(1))).thenReturn(HASH);
        final FakeEvent fakeEvent = new FakeEvent(EVENT_ID, AGGREGATE_ID, DESCRIPTION);

        // when
        final EventWrapper result = eventWrapperFactory.create(fakeEvent, 1);

        // then
        assertEquals(HASH, result.getEventHash());
        assertEquals(fakeEvent, result.getEvent());
        assertEquals(EVENT_ID, result.getEventId());
        assertEquals(AGGREGATE_ID, result.getAggregateId());
        assertEquals(1, result.getVersion());
        verify(eventHashFactory).create(eq(EVENT_ID), eq(AGGREGATE_ID), eq(1));
    }
}
