package com.nfaustino.splitmoney.owes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GroupTest {
    @Test
    void Should_AddParticipant() {
        var group = Group.builder().id(1).name("test group").build();
        var newParticipant = Participant.builder().id(1).name("John Doo").build();

        group.addParticipant(newParticipant);

        assertThat(group.participants).isNotEmpty().contains(newParticipant);
    }
}
