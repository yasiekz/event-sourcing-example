package io.yasiekz.github.eventsurcingexample.domain.aggregate;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Transient;

public abstract class EventSourcedAggregate {

    @Transient
    private final List<Event<?>> events = new ArrayList<>();
    @Transient
    private final List<UUID> pastEvents = new ArrayList<>();
    private int version = 0;

    protected EventSourcedAggregate() {
    }

    public void markEventsAsSaved() {
        version += getRecordedEvents().size();
        clearRecordedEvents();
    }

    public final EventSourcedAggregate applyPastEvent(Event<EventSourcedAggregate> event) {
        if (!pastEvents.contains(event.getId())) {
            version++;
            doApplyPastEvent(event);
            pastEvents.add(event.getId());
        }

        return this;
    }

    public List<Event<?>> getRecordedEvents() {
        return Collections.unmodifiableList(events);
    }

    public int getVersion() {
        return version;
    }

    protected void doApplyPastEvent(Event<EventSourcedAggregate> event) {
        event.apply(this);
    }

    protected void recordEvent(Event<?> event) {
        events.add(event);
    }

    private void clearRecordedEvents() {
        events.forEach(e -> pastEvents.add(e.getId()));
        events.clear();
    }
}
