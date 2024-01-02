package com.nfaustino.splitmoney.infra.db.data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class SummaryDataId {
    @ManyToOne
    @JoinColumn(name = "from_id")
    ParticipantData fromParticipant;

    @ManyToOne
    @JoinColumn(name = "to_id")
    ParticipantData toParticipant;

    @ManyToOne
    @JoinColumn(name = "group_id")
    GroupData group;
}
