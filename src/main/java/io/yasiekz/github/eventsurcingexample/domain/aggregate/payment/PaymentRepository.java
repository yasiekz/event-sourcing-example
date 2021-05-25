package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedRepository;
import io.yasiekz.github.eventsurcingexample.domain.event.EventStore;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepository extends EventSourcedRepository<Payment> {

    public PaymentRepository(final EventStore eventStore) {
        super(eventStore);
    }

    @Override
    protected Payment createInstance() {
        return new Payment();
    }
}
