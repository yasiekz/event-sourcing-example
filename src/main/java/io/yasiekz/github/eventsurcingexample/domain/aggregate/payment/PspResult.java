package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
@ToString
public class PspResult {

    UUID pspId;

}
