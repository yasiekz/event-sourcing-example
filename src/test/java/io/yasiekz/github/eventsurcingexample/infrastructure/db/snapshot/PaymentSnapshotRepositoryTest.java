package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.Payment;
import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.TestPaymentProvider;
import io.yasiekz.github.eventsurcingexample.initializer.MongoFromDockerInitializer;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = MongoFromDockerInitializer.class)
@SpringBootTest
class PaymentSnapshotRepositoryTest {

    private static final UUID AGGREGATE_ID = UUID.randomUUID();

    @Autowired
    private SnapshotMongoRepository<Payment> snapshotRepository;

    @Autowired
    private PaymentSnapshotRepository paymentSnapshotRepository;

    @AfterEach
    void tearDown() {
        snapshotRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save and then load snapshot from aggregate")
    void shouldSaveAndLoadAggregate() {

        // given
        final Payment payment = TestPaymentProvider.create(AGGREGATE_ID);

        // when
        paymentSnapshotRepository.save(payment);
        final Snapshot<Payment> result = paymentSnapshotRepository.load(AGGREGATE_ID);

        // then
        assertEquals(AGGREGATE_ID, result.getAggregateId());
    }
}
