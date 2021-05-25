package io.yasiekz.github.eventsurcingexample.domain.event;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of = {"id"})
@Getter
@RequiredArgsConstructor
public abstract class Event<T extends EventSourcedAggregate> {

    private final UUID id;
    private final UUID aggregateId;
    private final LocalDateTime occuredAt;

    public abstract void apply(final T aggregate);
}
