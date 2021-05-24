package io.yasiekz.github.eventsurcingexample.domain.aggregate;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class EventSourcedAggregate<T extends Event> {

    private final List<T> events = new ArrayList<>();
    private final List<UUID> pastEvents = new ArrayList<>();
    private int version = 0;

    public void markEventsAsSaved() {
        version += getRecordedEvents().size();
        clearRecordedEvents();
    }

    public final EventSourcedAggregate<?> applyPastEvent(T event) {
        if (!pastEvents.contains(event.getId())) {
            incrementVersion();
            doApplyPastEvent(event);
            pastEvents.add(event.getId());
        }

        return this;
    }

    public List<T> getRecordedEvents() {
        return Collections.unmodifiableList(events);
    }

    public int getVersion() {
        return version;
    }

    protected abstract void doApplyPastEvent(T event);

    protected void recordEvent(T event) {
        events.add(event);
    }

    private void incrementVersion() {
        version++;
    }

    private void clearRecordedEvents() {
        events.forEach(e -> pastEvents.add(e.getId()));
        events.clear();
    }
}
