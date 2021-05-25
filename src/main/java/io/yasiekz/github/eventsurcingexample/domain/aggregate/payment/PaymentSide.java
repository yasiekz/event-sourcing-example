package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.AccountNumber;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Builder(setterPrefix = "with")
public class PaymentSide {

    String name;
    String address;
    AccountNumber accountNumber;

}
