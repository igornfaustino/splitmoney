package com.nfaustino.splitmoney.debts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class DebtSummaryTest {
    @Test
    void should_AddDebtToCorrectPairs() {
        var summary = new DebtSummary();

        summary.addDebt(1, 2, BigDecimal.valueOf(10));
        summary.addDebt(2, 1, BigDecimal.valueOf(5));
        summary.addDebt(2, 1, BigDecimal.valueOf(20));

        assertThat(summary.getDebt(1, 2)).isEqualTo(BigDecimal.valueOf(-15));
        assertThat(summary.getDebt(2, 1)).isEqualTo(BigDecimal.valueOf(15));
    }

    @Test
    void should_ReturnZeroWhenNoDebt() {
        var summary = new DebtSummary();

        assertThat(summary.getDebt(1, 2)).isEqualTo(BigDecimal.ZERO);
        assertThat(summary.getDebt(2, 1)).isEqualTo(BigDecimal.ZERO);
    }
}
