package com.nfaustino.splitmoney.shared.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MoneyTest {
    @Test
    void should_PrintMoney() {
        var money = Money.real(10.5);

        assertThat(money.toString()).isEqualTo("R$ 10.50");
    }

}
