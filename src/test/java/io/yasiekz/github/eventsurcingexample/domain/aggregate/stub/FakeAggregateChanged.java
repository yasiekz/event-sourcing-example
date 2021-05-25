package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.time.LocalDateTime;
import java.util.UUID;

public class FakeAggregateChanged extends Event<FakeAggregate> {

    private final String newDescription;

    public FakeAggregateChanged(final UUID id, final UUID aggregateId, final String newDescription,
        final LocalDateTime occuredAt) {
        super(id, aggregateId, occuredAt);
        this.newDescription = newDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }

    @Override
    public void apply(final FakeAggregate aggregate) {
        aggregate.apply(this);
    }
}
