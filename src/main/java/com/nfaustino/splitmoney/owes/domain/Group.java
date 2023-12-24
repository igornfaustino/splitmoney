package com.nfaustino.splitmoney.owes.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    int id;
    String name;

    @Builder.Default
    List<Participant> participants = new ArrayList<Participant>();

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }
}
