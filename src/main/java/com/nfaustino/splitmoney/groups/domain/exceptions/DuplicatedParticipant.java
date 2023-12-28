package com.nfaustino.splitmoney.groups.domain.exceptions;

public class DuplicatedParticipant extends RuntimeException {
    public DuplicatedParticipant(String name) {
        super("Participant with name %s already exists".formatted(name));
    }
}
