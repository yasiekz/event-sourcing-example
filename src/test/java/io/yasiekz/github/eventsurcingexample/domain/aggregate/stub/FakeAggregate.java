package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.util.UUID;

public class FakeAggregate extends EventSourcedAggregate {

    private UUID id;
    private String description;

    public FakeAggregate(final UUID id) {
        recordEvent(new FakeAggregateCreated(UUID.randomUUID(), id));
    }

    FakeAggregate() {
        super();
    }

    public void changeDescription(final String newDescription) {
        recordEvent(new FakeAggregateChanged(UUID.randomUUID(), id, newDescription));
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    void applyEvent(FakeAggregateCreated event) {
        id = event.getAggregateId();
    }

    void applyEvent(FakeAggregateChanged event) {
        description = event.getNewDescription();
    }
}
