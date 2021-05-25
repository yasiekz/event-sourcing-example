package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.util.UUID;

public class FakeAggregateCreated extends Event<FakeAggregate> {

    public FakeAggregateCreated(final UUID id, final UUID aggregateId) {
        super(id, aggregateId);
    }

    public void apply(final FakeAggregate aggregate) {
        aggregate.applyEvent(this);
    }
}
