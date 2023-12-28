package com.nfaustino.splitmoney.groups.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.nfaustino.splitmoney.groups.domain.exceptions.DuplicatedParticipant;

public class GroupTest {
	@Test
	void Should_AddParticipant() {
		var group = createGroup();
		var newParticipant = Participant.builder().id(1).name("John Doo").build();

		group.addParticipant(newParticipant);

		assertThat(group.participants).isNotEmpty().contains(newParticipant);
	}

	@ParameterizedTest
	@MethodSource
	void Should_NOTAddParticipantWithSameName(List<Participant> participants) {
		var group = createGroup();

		group.addParticipant(participants.get(0));

		assertThatThrownBy(() -> group.addParticipant(participants.get(1)))
				.isInstanceOf(DuplicatedParticipant.class)
				.hasMessage("Participant with name %s already exists".formatted(participants.get(1).name.trim()));
	}

	private Group createGroup() {
		return Group.builder().id(1).name("test group").build();
	}

	private static Stream<List<Participant>> Should_NOTAddParticipantWithSameName() {
		return Stream.of(
				List.of(Participant.builder().id(1).name("John Doo").build(),
						Participant.builder().id(1).name("John Doo").build()),
				List.of(Participant.builder().id(1).name("John Doo").build(),
						Participant.builder().id(1).name(" John Doo").build()),
				List.of(Participant.builder().id(1).name("John Doo  ").build(),
						Participant.builder().id(1).name("John Doo").build()));
	}
}
