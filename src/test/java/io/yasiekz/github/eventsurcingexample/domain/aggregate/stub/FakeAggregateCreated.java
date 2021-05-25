package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.time.LocalDateTime;
import java.util.UUID;

public class FakeAggregateCreated extends Event<FakeAggregate> {

    public FakeAggregateCreated(final UUID id, final UUID aggregateId, final LocalDateTime occuredAt) {
        super(id, aggregateId, occuredAt);
    }

    public void apply(final FakeAggregate aggregate) {
        aggregate.apply(this);
    }
}
