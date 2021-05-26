package io.yasiekz.github.eventsurcingexample.infrastructure.db;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.Payment;
import io.yasiekz.github.eventsurcingexample.domain.event.EventSourcedAggregateLoader;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot.Snapshot;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot.SnapshotLoader;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentSnapshotAndEventsAggregateReader implements EventSourcedAggregateLoader<Payment> {

    private final SnapshotLoader<Payment> snapshotLoader;
    private final EventStore eventStore;

    @Override
    public Payment load(final UUID aggregateId) {

        final Snapshot<Payment> snapshot = snapshotLoader.load(aggregateId);

        Payment payment;
        int version;
        if (snapshot != null) {
            payment = snapshot.getAggregate();
            version = snapshot.getAggregateVersion();
        } else {
            payment = createPayment();
            version = 0;
        }

        eventStore.getForAggregate(aggregateId, version).forEach(payment::applyPastEvent);

        return payment;
    }

    private Payment createPayment() {
        return new Payment();
    }
}
