package io.yasiekz.github.eventsurcingexample.infrastructure.db.event;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class EventHashFactory {

    public String create(final UUID eventId, final UUID aggregateId, final int version) {

        String toHash = eventId.toString() + aggregateId.toString() + version;
        return DigestUtils.md5DigestAsHex(toHash.getBytes(StandardCharsets.UTF_8));
    }
}
