package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class PaymentDataModifiedEvent extends Event<Payment> {

    private final PaymentSide sender;
    private final PaymentSide receiver;

    @Builder(setterPrefix = "with")
    public PaymentDataModifiedEvent(final UUID id, final UUID aggregateId,
        final PaymentSide sender, final PaymentSide receiver, final LocalDateTime occuredAt) {
        super(id, aggregateId, occuredAt);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void apply(final Payment aggregate) {
        aggregate.apply(this);
    }
}
