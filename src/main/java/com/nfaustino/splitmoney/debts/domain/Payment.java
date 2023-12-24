package com.nfaustino.splitmoney.debts.domain;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    int from;
    BigDecimal value;
    String description;

    @Builder.Default
    Instant date = Instant.now();
}
