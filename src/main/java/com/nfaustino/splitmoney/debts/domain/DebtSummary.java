package com.nfaustino.splitmoney.debts.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebtSummary {
    @Builder.Default
    Map<Pair<Integer, Integer>, BigDecimal> summary = new HashMap<>();

    public void addDebt(int payer, int debtor, BigDecimal value) {
        var fromToCurrentDebt = getDebt(payer, debtor);
        putDebt(payer, debtor, fromToCurrentDebt.add(value));

        var toFromCurrentDebt = getDebt(debtor, payer);
        putDebt(debtor, payer, toFromCurrentDebt.subtract(value));
    }

    public BigDecimal getDebt(int from, int to) {
        return summary.getOrDefault(Pair.with(from, to), BigDecimal.ZERO);
    }

    private void putDebt(int from, int to, BigDecimal value) {
        summary.put(Pair.with(from, to), value);
    }
}
