package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@EqualsAndHashCode
@ToString
public class AmlScoring {

    int score;

}
