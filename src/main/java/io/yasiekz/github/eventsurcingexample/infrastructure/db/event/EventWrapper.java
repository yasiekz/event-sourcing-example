package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import io.yasiekz.github.eventsurcingexample.domain.event.Event;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "event")
@CompoundIndexes({
    @CompoundIndex(name = "aggregate_id_version", def = "{'aggregateId' : 1, 'version': 1}", unique = true)
})
public class EventWrapper {

    @Id
    private ObjectId id;
    @Indexed(background = true)
    private UUID aggregateId;
    @Indexed(unique = true)
    private UUID eventId;
    private Event event;
    @Indexed(unique = true)
    private String eventHash;
    @Indexed(background = true)
    private int version;
    private LocalDateTime createdAt;
    @Indexed(background = true)
    private String chunkId;

    @Builder(setterPrefix = "with")
    public EventWrapper(final Event event, final int version, final String eventHash) {
        aggregateId = event.getAggregateId();
        this.eventHash = eventHash;
        this.eventId = event.getId();
        this.event = event;
        this.version = version;
        createdAt = LocalDateTime.now();
    }
}
