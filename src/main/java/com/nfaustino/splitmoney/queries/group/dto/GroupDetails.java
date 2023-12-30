package com.nfaustino.splitmoney.queries.group.dto;

import java.util.List;

public record GroupDetails(int id, String name, List<ParticipantDetails> participants) {
}
