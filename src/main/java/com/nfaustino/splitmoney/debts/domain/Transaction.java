package com.nfaustino.splitmoney.debts.domain;

import java.time.Instant;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Transaction {
    Money value;
    int from;
    int to;
    String description;
    Instant createDate;
}
