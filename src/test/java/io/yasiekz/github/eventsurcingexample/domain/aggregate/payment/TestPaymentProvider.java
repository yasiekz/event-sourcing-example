package io.yasiekz.github.eventsurcingexample.domain.aggregate.payment;

import io.yasiekz.github.eventsurcingexample.domain.AccountNumber;
import io.yasiekz.github.eventsurcingexample.domain.Money;
import java.math.BigDecimal;
import java.util.UUID;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

public class TestPaymentProvider {

    public static Payment create(final UUID id) {

        return new Payment(
            id,
            createSide(),
            createSide(),
            createMoney()
        );

    }

    public static PaymentSide createSide() {

        return PaymentSide.builder()
            .withName(RandomStringUtils.randomAlphabetic(10))
            .withAddress(RandomStringUtils.randomAlphabetic(10))
            .withAccountNumber(AccountNumber.builder().withValue(RandomStringUtils.randomNumeric(24)).build())
            .build();
    }

    public static Money createMoney() {

        return Money.builder()
            .withAmount(BigDecimal.TEN)
            .withCurrency("EUR")
            .build();
    }

}
