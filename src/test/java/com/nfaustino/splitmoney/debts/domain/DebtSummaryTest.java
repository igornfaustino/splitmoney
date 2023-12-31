package com.nfaustino.splitmoney.debts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

public class DebtSummaryTest {
    @Test
    void should_AddDebtToCorrectPairs() {
        var summary = new DebtSummary();

        summary.addDebt(1, 2, Money.real(10));
        summary.addDebt(2, 1, Money.real(5));
        summary.addDebt(2, 1, Money.real(20));

        assertThat(summary.getDebt(1, 2)).isEqualTo(Money.real(-15));
        assertThat(summary.getDebt(2, 1)).isEqualTo(Money.real(15));
    }

    @Test
    void should_ReturnZeroWhenNoDebt() {
        var summary = new DebtSummary();

        assertThat(summary.getDebt(1, 2)).isEqualTo(Money.real(BigDecimal.ZERO));
        assertThat(summary.getDebt(2, 1)).isEqualTo(Money.real(BigDecimal.ZERO));
    }
}
