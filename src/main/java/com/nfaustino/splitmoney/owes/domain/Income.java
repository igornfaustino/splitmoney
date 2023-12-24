package com.nfaustino.splitmoney.owes.domain;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Income extends Transaction {

    @Builder
    public Income(BigDecimal value, int from, int to, String description, Instant createDate) {
        super(value, from, to, description, createDate);
    }
}
