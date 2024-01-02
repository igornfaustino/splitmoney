package com.nfaustino.splitmoney.infra.db.data;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "summary")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@IdClass(SummaryDataId.class)
public class SummaryData {
    @Id
    @ManyToOne
    @JoinColumn(name = "from_id")
    ParticipantData fromParticipant;

    @Id
    @ManyToOne
    @JoinColumn(name = "to_id")
    ParticipantData toParticipant;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    GroupData group;

    BigDecimal value;

}
