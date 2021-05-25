package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.Money;
import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Payment extends EventSourcedAggregate {

    private UUID id;
    private PaymentSide sender;
    private PaymentSide receiver;
    private Money amount;
    private Status status;
    private AmlScoring amlScoring;
    private PspResult pspResult;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder(setterPrefix = "with")
    public Payment(final UUID id, final PaymentSide sender, final PaymentSide receiver, final Money amount) {

        final PaymentCreatedEvent event = PaymentCreatedEvent.builder()
            .withId(UUID.randomUUID())
            .withAggregateId(id)
            .withAmount(amount)
            .withSender(sender)
            .withReceiver(receiver)
            .withOccuredAt(LocalDateTime.now())
            .build();

        recordEvent(event);
        apply(event);
    }

    public void resolveAmlScoring(AmlScoring amlScoring) {
        if (amlScoring.getScore() <= 0) {
            final AmlAcceptedEvent event = AmlAcceptedEvent.builder()
                .withId(UUID.randomUUID())
                .withAggregateId(id)
                .withScoring(amlScoring)
                .withOccuredAt(LocalDateTime.now())
                .build();
            apply(event);
            recordEvent(event);
        } else {
            final AmlRejectedEvent event = AmlRejectedEvent.builder()
                .withId(UUID.randomUUID())
                .withAggregateId(id)
                .withScoring(amlScoring)
                .withOccuredAt(LocalDateTime.now())
                .build();
            apply(event);
            recordEvent(event);
        }
    }

    public void markAsPspConfirmed(PspResult pspResult) {

        final PspAcceptedEvent event = PspAcceptedEvent.builder()
            .withAggregateId(id)
            .withId(UUID.randomUUID())
            .withPspResult(pspResult)
            .withOccuredAt(LocalDateTime.now())
            .build();
        apply(event);
        recordEvent(event);
    }

    public void modify(final PaymentSide sender, final PaymentSide receiver) {

        final PaymentDataModifiedEvent event = PaymentDataModifiedEvent.builder()
            .withId(UUID.randomUUID())
            .withAggregateId(id)
            .withSender(sender)
            .withReceiver(receiver)
            .withOccuredAt(LocalDateTime.now())
            .build();

        apply(event);
        recordEvent(event);
    }

    void apply(final PaymentCreatedEvent event) {

        if (status != null) {
            throw new IllegalPaymentStateException(event);
        }

        id = event.getAggregateId();
        sender = event.getSender();
        receiver = event.getReceiver();
        amount = event.getAmount();
        status = Status.CREATED;
        createdAt = event.getOccuredAt();
        modifiedAt = event.getOccuredAt();
    }

    void apply(final AmlAcceptedEvent event) {

        if (status != Status.CREATED) {
            throw new IllegalPaymentStateException(event);
        }

        amlScoring = event.getScoring();
        status = Status.AML_ACCEPTED;
        modifiedAt = event.getOccuredAt();
    }

    void apply(final AmlRejectedEvent event) {

        if (status != Status.CREATED) {
            throw new IllegalPaymentStateException(event);
        }

        amlScoring = event.getScoring();
        status = Status.AML_REJECTED;
        modifiedAt = event.getOccuredAt();
    }

    void apply(final PspAcceptedEvent event) {

        if (status != Status.AML_ACCEPTED) {
            throw new IllegalPaymentStateException(event);
        }

        pspResult = event.getPspResult();
        status = Status.SETTLED;
        modifiedAt = event.getOccuredAt();
    }

    void apply(final PaymentDataModifiedEvent event) {

        if (status == Status.SETTLED) {
            throw new IllegalPaymentStateException(event);
        }

        sender = event.getSender();
        receiver = event.getReceiver();
        status = Status.CREATED;
        modifiedAt = event.getOccuredAt();
    }
}
