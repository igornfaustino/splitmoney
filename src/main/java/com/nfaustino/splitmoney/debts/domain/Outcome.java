package com.nfaustino.splitmoney.debts.domain;

import java.time.Instant;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Outcome extends Transaction {

    @Builder
    public Outcome(Money value, int from, int to, String description, Instant createDate) {
        super(value, from, to, description, createDate);
    }

}
