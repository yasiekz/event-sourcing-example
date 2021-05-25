package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.AmlScoring;
import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.Payment;
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
public class AmlRejectedEvent extends Event<Payment> {

    private final AmlScoring scoring;

    @Builder(setterPrefix = "with")
    public AmlRejectedEvent(final UUID id, final UUID aggregateId,
        final AmlScoring scoring, final LocalDateTime occuredAt) {
        super(id, aggregateId, occuredAt);
        this.scoring = scoring;
    }

    @Override
    public void apply(final Payment aggregate) {
        aggregate.apply(this);
    }
}
