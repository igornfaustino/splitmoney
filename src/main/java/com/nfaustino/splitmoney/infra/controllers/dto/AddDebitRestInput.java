package com.nfaustino.splitmoney.infra.controllers.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddDebitRestInput {
    @NotNull
    Instant date;

    @NotNull
    int from;

    @NotNull
    double value;

    String description;
}
