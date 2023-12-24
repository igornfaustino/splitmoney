package com.nfaustino.splitmoney.debts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GroupTest {
    @Test
    void Should_AddParticipant() {
        var group = Group.builder().id(1).name("test group").build();
        var newParticipant = Participant.builder().id(1).name("John Doo").build();

        group.addParticipant(newParticipant);

        assertThat(group.participants).isNotEmpty().contains(newParticipant);
    }

    @Test
    void Should_AddSplitValueWithEveryParticipant() {
        var group = Group.builder().id(1).name("test group").build();
        var participant1 = Participant.builder().id(1).name("John Doo").build();
        var participant2 = Participant.builder().id(2).name("John Doo").build();
        var participant3 = Participant.builder().id(3).name("John Doo").build();
        group.addParticipant(participant1);
        group.addParticipant(participant2);
        group.addParticipant(participant3);
        var value = BigDecimal.valueOf(30.6);
        var date = Instant.now();
        var description = "test payment";

        group.addPaymentSplitEqual(Payment.builder()
                .from(participant1.getId())
                .value(value)
                .date(date)
                .description(description)
                .build());

        assertThat(group.history)
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        Outcome.builder()
                                .from(participant1.getId())
                                .to(participant2.getId())
                                .value(BigDecimal.valueOf(10.2))
                                .createDate(date)
                                .description(description)
                                .build(),
                        Outcome.builder()
                                .from(participant1.getId())
                                .to(participant3.getId())
                                .value(BigDecimal.valueOf(10.2))
                                .createDate(date)
                                .description(description)
                                .build());
    }

    @Test
    void Should_AddSplitValueWithSpecificParticipants() {
        var group = Group.builder().id(1).name("test group").build();
        var participant1 = Participant.builder().id(1).name("John Doo").build();
        var participant2 = Participant.builder().id(2).name("John Doo").build();
        var participant3 = Participant.builder().id(3).name("John Doo").build();
        group.addParticipant(participant1);
        group.addParticipant(participant2);
        group.addParticipant(participant3);
        var value = BigDecimal.valueOf(30.6);
        var date = Instant.now();
        var description = "test payment";

        group.addPaymentSplitEqual(Payment.builder()
                .from(participant1.getId())
                .value(value)
                .date(date)
                .description(description)
                .build(), List.of(participant2));

        assertThat(group.history)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(
                        Outcome.builder()
                                .from(participant1.getId())
                                .to(participant2.getId())
                                .value(BigDecimal.valueOf(15.3))
                                .createDate(date)
                                .description(description)
                                .build());
    }
}
