package com.nfaustino.splitmoney.infra.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.nfaustino.splitmoney.debts.domain.Group;
import com.nfaustino.splitmoney.debts.domain.Participant;
import com.nfaustino.splitmoney.debts.domain.Payment;
import com.nfaustino.splitmoney.infra.db.repositories.HistoryRepository;
import com.nfaustino.splitmoney.shared.valueobjects.Money;
import com.nfaustino.splitmoney.utils.DatabaseTest;

@SpringBootTest
public class GroupDebitServiceDataTest extends DatabaseTest {
    @Autowired
    GroupDebitServiceData groupDebitServiceData;

    @Autowired
    HistoryRepository historyRepository;

    @Test
    @DataSet(value = "valid-group-with-participants.yml", cleanBefore = true, cleanAfter = true)
    void should_getGroupWithParticipants() {
        var optionalGroup = groupDebitServiceData.getGroupById(1000);

        assertThat(optionalGroup).isPresent();
        var group = optionalGroup.get();
        assertThat(group.getParticipants()).hasSize(3).contains(
                Participant.builder().id(1000).build(),
                Participant.builder().id(1001).build(),
                Participant.builder().id(1002).build());
    }

    @Test
    @DataSet(value = "valid-group-with-participants.yml", cleanBefore = true, cleanAfter = true)
    void should_SaveSummaryAndHistory() {
        var group = Group.builder()
                .id(1000)
                .participants(
                        List.of(
                                Participant.builder().id(1000).build(),
                                Participant.builder().id(1001).build(),
                                Participant.builder().id(1002).build()))
                .build();
        group.addPaymentSplitEqual(Payment.builder()
                .date(Instant.parse("2007-12-03T10:15:30.00Z"))
                .value(Money.real(90.30))
                .from(1000)
                .description("test payment divided in 3")
                .build());

        groupDebitServiceData.saveSummaryAndHistory(group);

        var history = historyRepository.findByGroupId(1000);
        assertThat(history).hasSize(2);
        var testHistoryList = history.stream().map(h -> new TestHistory(h.getDescription(),
                h.getFromParticipant().getId(),
                h.getToParticipant().getId(),
                Money.real(h.getValue()))).toList();
        assertThat(testHistoryList).contains(
                new TestHistory("test payment divided in 3", 1000, 1001, Money.real(30.10)),
                new TestHistory("test payment divided in 3", 1000, 1002, Money.real(30.10)));
        var optionalGroup = groupDebitServiceData.getGroupById(1000);
        assertThat(optionalGroup).isPresent();
        var savedGroup = optionalGroup.get();
        assertThat(savedGroup.getHistory()).isEmpty();
        assertThat(savedGroup.getDebtSummary().getDebt(1000, 1001)).isEqualTo(Money.real(30.10));
        assertThat(savedGroup.getDebtSummary().getDebt(1000, 1002)).isEqualTo(Money.real(30.10));
        assertThat(savedGroup.getDebtSummary().getDebt(1001, 1000)).isEqualTo(Money.real(-30.10));
        assertThat(savedGroup.getDebtSummary().getDebt(1002, 1000)).isEqualTo(Money.real(-30.10));
    }
}

record TestHistory(String description, Integer from, Integer to, Money value) {
}