package com.nfaustino.splitmoney.debts.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    int id;
    String name;

    @Builder.Default
    List<Transaction> history = new ArrayList<>();

    @Builder.Default
    List<Participant> participants = new ArrayList<>();

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    public void addPaymentSplitEqual(Payment payment) {
        addPaymentSplitEqual(payment, this.participants);
    }

    public void addPaymentSplitEqual(Payment payment, List<Participant> participants) {
        var numberOfParticipants = getNumberOfParticipants(payment.getFrom(), participants);
        var valueForEach = payment.value.divide(BigDecimal.valueOf(numberOfParticipants));
        var newTransactions = participants.stream()
                .filter(participant -> participant.getId() != payment.from)
                .map(participant -> {
                    return Outcome.builder()
                            .from(payment.from)
                            .to(participant.getId())
                            .createDate(payment.getDate())
                            .description(payment.getDescription())
                            .value(valueForEach)
                            .build();
                }).toList();
        history.addAll(newTransactions);
    }

    private int getNumberOfParticipants(int from, List<Participant> participants) {
        var participantIds = new HashSet<Integer>();
        participantIds.add(from);
        participantIds.addAll(participants.stream().map(p -> p.getId()).toList());
        return participantIds.size();
    }
}
