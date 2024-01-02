package com.nfaustino.splitmoney.infra.db.data;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "history")
@NoArgsConstructor
@AllArgsConstructor
public class HistoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String description;

    @ManyToOne
    @JoinColumn(name = "from_id", table = "history")
    ParticipantData fromParticipant;

    @ManyToOne
    @JoinColumn(name = "to_id", table = "history")
    ParticipantData toParticipant;

    BigDecimal value;

    Instant transactionDate;

    @ManyToOne
    @JoinColumn(name = "group_id", table = "history")
    GroupData group;
}
