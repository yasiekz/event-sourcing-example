package io.yasiekz.github.eventsurcingexample.domain.event;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;

public interface EventSourcedAggregateSaver<T extends EventSourcedAggregate> {

    void save(T aggregate);

}
