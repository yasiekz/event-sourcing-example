package io.yasiekz.github.eventsurcingexample.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@EqualsAndHashCode
@ToString
public class Money {

    BigDecimal amount;
    String currency;

}
