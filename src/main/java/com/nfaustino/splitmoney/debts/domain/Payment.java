package com.nfaustino.splitmoney.debts.domain;

import java.time.Instant;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    int from;
    Money value;
    String description;

    @Builder.Default
    Instant date = Instant.now();
}
