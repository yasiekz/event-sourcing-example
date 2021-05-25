package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.Money;
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
public class PaymentCreatedEvent extends Event<Payment> {

    private final PaymentSide sender;
    private final PaymentSide receiver;
    private final Money amount;

    @Builder(setterPrefix = "with")
    public PaymentCreatedEvent(final UUID id, final UUID aggregateId,
        final PaymentSide sender, final PaymentSide receiver, final Money amount, final LocalDateTime occuredAt) {
        super(id, aggregateId, occuredAt);
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public void apply(final Payment aggregate) {
        aggregate.apply(this);
    }
}
