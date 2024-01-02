package com.nfaustino.splitmoney.infra.controllers.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record HistoryDetails(int from, int to, String description, BigDecimal value,
        Instant date) {

}
