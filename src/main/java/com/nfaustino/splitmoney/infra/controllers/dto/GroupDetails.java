package com.nfaustino.splitmoney.infra.controllers.dto;

import java.util.List;

public record GroupDetails(int id, String name, List<ParticipantDetails> participants,
                List<HistoryDetails> groupHistory, List<SummaryDetails> summary) {
}
