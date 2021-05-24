package io.yasiekz.github.eventsurcingexample.domain.event;

public class EventDuplicatedException extends RuntimeException {

    private static final long serialVersionUID = -856558950047245858L;

    public EventDuplicatedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
