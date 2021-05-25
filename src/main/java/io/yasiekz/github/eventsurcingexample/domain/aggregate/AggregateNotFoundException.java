package io.yasiekz.github.eventsurcingexample.domain.aggregate;

import java.util.UUID;

public class AggregateNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Aggregate with ID=%s not found";

    private static final long serialVersionUID = -7804633907996684702L;

    public AggregateNotFoundException(final UUID aggregateId) {
        super(String.format(MESSAGE, aggregateId));
    }
}
