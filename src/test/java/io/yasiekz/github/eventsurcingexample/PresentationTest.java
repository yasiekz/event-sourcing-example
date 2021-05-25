package io.yasiekz.github.eventsurcingexample;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.*;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store.EventMongoRepository;
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

    @AfterEach
    void tearDown() {
        eventMongoRepository.deleteAll();
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
}
