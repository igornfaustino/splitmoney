package com.nfaustino.splitmoney.debts.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.nfaustino.splitmoney.shared.valueobjects.Money;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    int id;

    @Builder.Default
    List<Transaction> history = new ArrayList<>();

    @Builder.Default
    List<Participant> participants = new ArrayList<>();

    @Builder.Default
    DebtSummary debtSummary = new DebtSummary();

    public void addPaymentSplitEqual(Payment payment) {
        addPaymentSplitEqual(payment, this.participants.stream().map(Participant::getId).toList());
    }

    public void addPaymentSplitEqual(Payment payment, List<Integer> participants) {
        var numberOfParticipants = getNumberOfParticipants(payment.getFrom(), participants);
        var valueForEach = Money
                .real(payment.value.divide(BigDecimal.valueOf(numberOfParticipants), 2, RoundingMode.HALF_EVEN));
        var newTransactions = participants.stream()
                .filter(participantId -> participantId != payment.from)
                .map(participantId -> {
                    debtSummary.addDebt(payment.from, participantId, valueForEach);
                    return Transaction.builder()
                            .from(payment.from)
                            .to(participantId)
                            .createDate(payment.getDate())
                            .description(payment.getDescription())
                            .value(valueForEach)
                            .build();
                }).toList();
        history.addAll(newTransactions);
    }

    private int getNumberOfParticipants(int from, List<Integer> participants) {
        var uniqueParticipantIds = new HashSet<Integer>();
        uniqueParticipantIds.add(from);
        uniqueParticipantIds.addAll(participants);
        return uniqueParticipantIds.size();
    }
}
