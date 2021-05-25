package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;

public class IllegalPaymentStateException extends RuntimeException{

    private static final String MESSAGE = "Cannot apply event %s for aggregate with ID=%s";

    private static final long serialVersionUID = -5862528406671525063L;

    public IllegalPaymentStateException(final Event<?> event) {
        super(String.format(MESSAGE, event.toString(), event.getAggregateId()));
    }
}
