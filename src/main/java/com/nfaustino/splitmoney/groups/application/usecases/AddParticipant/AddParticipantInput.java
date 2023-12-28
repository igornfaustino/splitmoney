package com.nfaustino.splitmoney.groups.application.usecases.AddParticipant;

import lombok.Builder;

@Builder
public record AddParticipantInput(int groupId, String name) {

}
