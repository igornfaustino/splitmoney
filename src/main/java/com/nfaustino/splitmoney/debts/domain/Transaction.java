package com.nfaustino.splitmoney.debts.domain;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Transaction {
    BigDecimal value;
    int from;
    int to;
    String description;
    Instant createDate;
}
