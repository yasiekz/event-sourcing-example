package io.yasiekz.github.eventsurcingexample.domain.aggregate.stub;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedRepository;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;

public class FakeEventSourcedRepository extends EventSourcedRepository<FakeAggregate> {

    public FakeEventSourcedRepository(final EventStore eventStore) {
        super(eventStore);
    }

    @Override
    protected FakeAggregate createInstance() {
        return new FakeAggregate();
    }
}
