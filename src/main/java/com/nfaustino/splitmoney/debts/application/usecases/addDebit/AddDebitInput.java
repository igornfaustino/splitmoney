package com.nfaustino.splitmoney.debts.application.usecases.addDebit;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Builder;

@Builder
public record AddDebitInput(int groupId, Instant date, int from, BigDecimal value, String description,
        List<Integer> participants) {

}
