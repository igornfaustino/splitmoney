package com.nfaustino.splitmoney.debts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

public class GroupTest {
	@Test
	void Should_AddSplitValueWithEveryParticipant() {
		var group = createGroup();
		var participants = createParticipants(3);
		group.setParticipants(participants);
		var value = BigDecimal.valueOf(30.6);
		var date = Instant.now();
		var description = "test payment";

		group.addPaymentSplitEqual(Payment.builder()
				.from(participants.get(0).getId())
				.value(value)
				.date(date)
				.description(description)
				.build());

		var participant1 = participants.get(0).getId();
		var participant2 = participants.get(1).getId();
		var participant3 = participants.get(2).getId();
		assertThat(group.history)
				.isNotEmpty()
				.hasSize(2)
				.containsExactly(
						Transaction.builder()
								.from(participant1)
								.to(participant2)
								.value(Money.real(10.2))
								.createDate(date)
								.description(description)
								.build(),
						Transaction.builder()
								.from(participant1)
								.to(participant3)
								.value(Money.real(10.2))
								.createDate(date)
								.description(description)
								.build());
		assertThat(group.debtSummary.getDebt(participant1, participant2))
				.isEqualTo(Money.real(10.2));
		assertThat(group.debtSummary.getDebt(participant1, participant3))
				.isEqualTo(Money.real(10.2));
		assertThat(group.debtSummary.getDebt(participant2, participant1))
				.isEqualTo(Money.real(-10.2));
		assertThat(group.debtSummary.getDebt(participant3, participant1))
				.isEqualTo(Money.real(-10.2));
	}

	@Test
	void Should_AddSplitValueWithSpecificParticipants() {
		var group = createGroup();
		var participants = createParticipants(3);
		group.setParticipants(participants);
		var value = BigDecimal.valueOf(30.6);
		var date = Instant.now();
		var description = "test payment";

		group.addPaymentSplitEqual(Payment.builder()
				.from(participants.get(0).getId())
				.value(value)
				.date(date)
				.description(description)
				.build(), List.of(participants.get(1).getId()));

		var participant1 = participants.get(0).getId();
		var participant2 = participants.get(1).getId();
		var participant3 = participants.get(2).getId();
		assertThat(group.history)
				.isNotEmpty()
				.hasSize(1)
				.containsExactly(
						Transaction.builder()
								.from(participant1)
								.to(participant2)
								.value(Money.real(15.3))
								.createDate(date)
								.description(description)
								.build());
		assertThat(group.debtSummary.getDebt(participant1, participant2))
				.isEqualTo(Money.real(15.3));
		assertThat(group.debtSummary.getDebt(participant1, participant3))
				.isEqualTo(Money.real(0));
		assertThat(group.debtSummary.getDebt(participant2, participant1))
				.isEqualTo(Money.real(-15.3));
		assertThat(group.debtSummary.getDebt(participant3, participant1))
				.isEqualTo(Money.real(0));
	}

	private Group createGroup() {
		return Group.builder().id(1).build();
	}

	List<Participant> createParticipants(int quantity) {
		List<Participant> participants = new ArrayList<>();
		for (int i = 0; i < quantity; i++) {
			participants.add(Participant.builder().id(i + 1).build());
		}
		return participants;
	}
}
