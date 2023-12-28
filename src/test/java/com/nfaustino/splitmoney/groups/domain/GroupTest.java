package com.nfaustino.splitmoney.groups.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GroupTest {
	@Test
	void Should_AddParticipant() {
		var group = createGroup();
		var newParticipant = Participant.builder().id(1).name("John Doo").build();

		group.addParticipant(newParticipant);

		assertThat(group.participants).isNotEmpty().contains(newParticipant);
	}

	private Group createGroup() {
		return Group.builder().id(1).name("test group").build();
	}

	List<Participant> createParticipants(int quantity) {
		List<Participant> participants = new ArrayList<>();
		for (int i = 0; i < quantity; i++) {
			participants.add(Participant.builder().id(i + 1).build());
		}
		return participants;
	}
}
