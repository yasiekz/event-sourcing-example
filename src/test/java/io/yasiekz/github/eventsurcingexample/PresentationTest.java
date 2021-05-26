package io.yasiekz.github.eventsurcingexample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.*;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store.EventMongoRepository;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot.SnapshotGenerator;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot.SnapshotMongoRepository;
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
public class PresentationTest {

    private static final UUID PAYMENT_ID = UUID.randomUUID();

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EventMongoRepository eventMongoRepository;

    @Autowired
    private SnapshotGenerator snapshotGenerator;

    @Autowired
    private SnapshotMongoRepository<Payment> snapshotRepository;

    @AfterEach
    void tearDown() {
        eventMongoRepository.deleteAll();
        snapshotRepository.deleteAll();
    }

    @Test
    @DisplayName("Create payment and process it")
    void t1() {

        final Payment payment = TestPaymentProvider.create(PAYMENT_ID);
        payment.modify(
            TestPaymentProvider.createSide(),
            TestPaymentProvider.createSide()
        );
        payment.resolveAmlScoring(AmlScoring.builder().withScore(0).build());
        payment.markAsPspConfirmed(PspResult.builder().pspId(UUID.randomUUID()).build());
        paymentRepository.save(payment);
    }

    @Test
    @DisplayName("Crate payment, save, load and then continue processing")
    void t2() {

        // create and save
        final Payment payment = TestPaymentProvider.create(PAYMENT_ID);
        paymentRepository.save(payment);

        // load
        final Payment result = paymentRepository.load(PAYMENT_ID);

        // change and save again
        result.resolveAmlScoring(AmlScoring.builder().withScore(0).build());
        result.markAsPspConfirmed(PspResult.builder().pspId(UUID.randomUUID()).build());
        paymentRepository.save(result);
    }

    @Test
    @DisplayName("Crate two aggregates and make snapshot for them")
    void t3() {

        // create aggregates
        final Payment payment1 = TestPaymentProvider.create(PAYMENT_ID);
        payment1.modify(TestPaymentProvider.createSide(), TestPaymentProvider.createSide());
        payment1.modify(TestPaymentProvider.createSide(), TestPaymentProvider.createSide());
        final Payment payment2 = TestPaymentProvider.create(UUID.randomUUID());
        payment2.modify(TestPaymentProvider.createSide(), TestPaymentProvider.createSide());
        payment2.modify(TestPaymentProvider.createSide(), TestPaymentProvider.createSide());
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        // generate snapshots for them
        snapshotGenerator.generate();

        // check if there are 2 snapshots
        assertEquals(2, snapshotRepository.count());
    }
}
