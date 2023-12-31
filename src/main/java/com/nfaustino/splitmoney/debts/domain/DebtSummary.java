package com.nfaustino.splitmoney.debts.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

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
    Map<Pair<Integer, Integer>, Money> summary = new HashMap<>();

    public void addDebt(int payer, int debtor, Money value) {
        var fromToCurrentDebt = getDebt(payer, debtor);
        putDebt(payer, debtor, fromToCurrentDebt.add(value));

        var toFromCurrentDebt = getDebt(debtor, payer);
        putDebt(debtor, payer, toFromCurrentDebt.subtract(value));
    }

    public Money getDebt(int from, int to) {
        return summary.getOrDefault(Pair.with(from, to), Money.real(BigDecimal.ZERO));
    }

    private void putDebt(int from, int to, Money value) {
        summary.put(Pair.with(from, to), value);
    }
}
