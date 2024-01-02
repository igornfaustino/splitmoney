package com.nfaustino.splitmoney.infra.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
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
    @DataSet(value = "expected/after-add-payment.yml", cleanBefore = true, cleanAfter = true)
    void should_getGroupWithParticipants() {
        var optionalGroup = groupDebitServiceData.getGroupById(1000);

        assertThat(optionalGroup).isPresent();
        var group = optionalGroup.get();
        assertThat(group.getParticipants()).hasSize(3).contains(
                Participant.builder().id(1000).build(),
                Participant.builder().id(1001).build(),
                Participant.builder().id(1002).build());
        assertThat(group.getHistory()).isEmpty();
        assertThat(group.getDebtSummary().getDebt(1000, 1001)).isEqualTo(Money.real(30.10));
        assertThat(group.getDebtSummary().getDebt(1000, 1002)).isEqualTo(Money.real(30.10));
        assertThat(group.getDebtSummary().getDebt(1001, 1000)).isEqualTo(Money.real(-30.10));
        assertThat(group.getDebtSummary().getDebt(1002, 1000)).isEqualTo(Money.real(-30.10));
    }

    @Test
    @DataSet(value = "valid-group-with-participants.yml", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "expected/after-add-payment.yml")
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
                .date(Instant.parse("2024-01-02T12:01:43.777Z"))
                .value(Money.real(90.30))
                .from(1000)
                .description("integration test")
                .build());

        groupDebitServiceData.saveSummaryAndHistory(group);
    }
}