package com.nfaustino.splitmoney.groups.domain.exceptions;

import com.nfaustino.splitmoney.shared.base.exceptions.BadRequest;

public class DuplicatedParticipant extends BadRequest {
    public DuplicatedParticipant(String name) {
        super("Participant with name %s already exists".formatted(name));
    }
}
