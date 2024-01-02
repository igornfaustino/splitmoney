package com.nfaustino.splitmoney.groups.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ParticipantTest {
    @Test
    void should_TrimParticipantNameOnBuild() {
        var participant = Participant.builder().name("test ").build();

        assertThat(participant.name).isEqualTo("test");
    }

    @Test
    void should_TrimParticipantNameOnSet() {
        var participant = Participant.builder().name("test").build();

        participant.setName(" other name ");

        assertThat(participant.name).isEqualTo("other name");
    }

    @Test
    void should_NotBreakWhenNameIsNull() {
        var participant = Participant.builder().name("test").build();

        participant.setName(null);

        assertThat(participant.name).isEqualTo("test");
    }

    @Test
    public void should_NotBreakWhenNameIsNullOnBuild() {
        var participant = Participant.builder().name(null).build();

        assertThat(participant).isNotNull();
    }
}
