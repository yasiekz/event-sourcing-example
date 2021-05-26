package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

import io.yasiekz.github.eventsurcingexample.infrastructure.db.event.store.EventWrapper;
import java.util.stream.Stream;

public interface EventToSnapshotGenerationReader {

    Stream<EventWrapper> findEventsForChunkAfterObjectId(final String chunkId);

}
