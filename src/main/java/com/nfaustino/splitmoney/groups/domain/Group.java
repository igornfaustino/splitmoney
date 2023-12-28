package com.nfaustino.splitmoney.groups.domain;

import java.util.ArrayList;
import java.util.List;

import com.nfaustino.splitmoney.groups.domain.exceptions.DuplicatedParticipant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    int id;
    String name;

    @Builder.Default
    List<Participant> participants = new ArrayList<>();

    public void addParticipant(Participant newParticipant) {
        var participantAlreadyIncluded = participants.stream()
                .anyMatch(groupParticipant -> groupParticipant.getName().equals(newParticipant.getName()));
        if (participantAlreadyIncluded)
            throw new DuplicatedParticipant(newParticipant.getName().trim());
        this.participants.add(newParticipant);
    }
}
