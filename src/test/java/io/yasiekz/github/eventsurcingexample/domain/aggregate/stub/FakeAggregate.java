package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FakeAggregate extends EventSourcedAggregate {

    private UUID id;
    private String description;

    public FakeAggregate(final UUID id) {
        final FakeAggregateCreated event = new FakeAggregateCreated(UUID.randomUUID(), id, LocalDateTime.now());
        recordEvent(event);
        apply(event);
    }

    FakeAggregate() {
        super();
    }

    public void changeDescription(final String newDescription) {
        final FakeAggregateChanged event = new FakeAggregateChanged(UUID.randomUUID(), id, newDescription,
            LocalDateTime.now());
        apply(event);
        recordEvent(event);
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    void apply(FakeAggregateCreated event) {
        id = event.getAggregateId();
    }

    void apply(FakeAggregateChanged event) {
        description = event.getNewDescription();
    }
}
