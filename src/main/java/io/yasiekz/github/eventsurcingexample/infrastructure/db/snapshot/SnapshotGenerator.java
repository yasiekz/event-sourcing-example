package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.payment.Payment;
import io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store.EventWrapper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Slf4j
@RequiredArgsConstructor
@Component
public class SnapshotGenerator {

    private final EventToSnapshotGenerationReader eventToSnapshotGenerationReader;
    private final PaymentSnapshotRepository paymentSnapshotRepository;

    public void generate() {

        log.info("Snapshot creation started");

        for (EventChunk chunk : EventChunk.values()) {

            log.info("Generating snapshots for chunk {}", chunk.getValue());
            final Map<UUID, List<EventWrapper>> map = eventToSnapshotGenerationReader
                .findEventsForChunkAfterObjectId(chunk.getValue())
                .collect(Collectors.groupingBy(EventWrapper::getAggregateId));

            map.forEach((k, v) -> {
                log.info("Found aggregate {}", k);
                final Payment payment = new Payment();
                v.forEach(e -> {
                    log.info("Applying event {} for aggregate {}", e.getId(), e.getAggregateId());
                    payment.applyPastEvent(e.getEvent());
                });
                paymentSnapshotRepository.save(payment);
                log.info("Snapshot for aggregate {} saved", k);
            });

            log.info("Snapshot generation for chunk {} finished", chunk.getValue());
        }

        log.info("Snapshot creation finished");
    }
}
