package com.nfaustino.splitmoney.infra.controllers.dto;

import java.math.BigDecimal;

public record SummaryDetails(int from, int to, BigDecimal value) {
}
