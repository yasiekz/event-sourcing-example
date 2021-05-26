package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.domain.aggregate.EventSourcedAggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Getter
@Builder(setterPrefix = "with")
@Document
@CompoundIndexes({
    @CompoundIndex(name = "entity_version_unique", def = "{'aggregateId' : 1, 'aggregateVersion' : 1}", unique = true)})
public final class Snapshot<T extends EventSourcedAggregate> {

    @Id
    private final ObjectId id;
    @Indexed(background = true)
    private final UUID aggregateId;
    private final T aggregate;
    @Indexed(background = true)
    private final int aggregateVersion;
    private final LocalDateTime createdAt;
}
