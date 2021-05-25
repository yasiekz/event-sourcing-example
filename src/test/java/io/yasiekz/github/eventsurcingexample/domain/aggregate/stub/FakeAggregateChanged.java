package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.util.UUID;

public class FakeAggregateChanged extends Event<FakeAggregate> {

    private final String newDescription;

    public FakeAggregateChanged(final UUID id, final UUID aggregateId, final String newDescription) {
        super(id, aggregateId);
        this.newDescription = newDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }

    @Override
    public void apply(final FakeAggregate aggregate) {
        aggregate.applyEvent(this);
    }
}
