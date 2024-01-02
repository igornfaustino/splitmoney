package com.nfaustino.splitmoney.infra.db.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "participant")
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @ManyToOne
    @JoinColumn(name = "group_id", table = "participant")
    GroupData group;

    @OneToMany(mappedBy = "fromParticipant", cascade = CascadeType.ALL)
    @Builder.Default
    List<HistoryData> historyFrom = new ArrayList<>();

    @OneToMany(mappedBy = "toParticipant", cascade = CascadeType.ALL)
    @Builder.Default
    List<HistoryData> historyTo = new ArrayList<>();

    @OneToMany(mappedBy = "fromParticipant", cascade = CascadeType.ALL)
    @Builder.Default
    List<SummaryData> summaryFrom = new ArrayList<>();

    @OneToMany(mappedBy = "toParticipant", cascade = CascadeType.ALL)
    @Builder.Default
    List<SummaryData> summaryTo = new ArrayList<>();
}
