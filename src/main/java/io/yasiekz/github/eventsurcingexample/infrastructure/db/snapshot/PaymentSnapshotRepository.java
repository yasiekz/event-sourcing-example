package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.Payment;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PaymentSnapshotRepository extends SnapshotRepository<Payment> {

    public PaymentSnapshotRepository(
        final SnapshotMongoRepository<Payment> snapshotMongoRepository) {
        super(snapshotMongoRepository);
    }

    @Override
    protected Snapshot<Payment> createInstance(final Payment aggregate) {

        return Snapshot.<Payment>builder()
            .withAggregateId(aggregate.getId())
            .withAggregate(aggregate)
            .withAggregateVersion(aggregate.getVersion())
            .withCreatedAt(LocalDateTime.now())
            .build();
    }
}
