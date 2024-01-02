package com.nfaustino.splitmoney.infra.services;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.nfaustino.splitmoney.debts.application.services.GroupDebitService;
import com.nfaustino.splitmoney.debts.domain.Group;
import com.nfaustino.splitmoney.debts.domain.exceptions.GroupNotFound;
import com.nfaustino.splitmoney.infra.db.data.HistoryData;
import com.nfaustino.splitmoney.infra.db.data.ParticipantData;
import com.nfaustino.splitmoney.infra.db.data.SummaryData;
import com.nfaustino.splitmoney.infra.db.repositories.GroupRepository;
import com.nfaustino.splitmoney.infra.db.repositories.HistoryRepository;
import com.nfaustino.splitmoney.infra.db.repositories.SummaryRepository;
import com.nfaustino.splitmoney.infra.mappers.GroupDebitMapper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class GroupDebitServiceData implements GroupDebitService {
    GroupRepository groupRepository;
    SummaryRepository summaryRepository;
    HistoryRepository historyRepository;
    GroupDebitMapper mapper;

    @Override
    @Transactional
    public Optional<Group> getGroupById(int id) {
        var result = groupRepository.findById(id);
        return result.map(mapper::fromGroupData);
    }

    @Override
    @Transactional
    public boolean saveSummaryAndHistory(Group group) {
        var groupData = groupRepository.findById(group.getId()).orElseThrow(() -> new GroupNotFound(group.getId()));
        Map<Integer, ParticipantData> participantsMap = groupData.getParticipants().stream()
                .collect(Collectors.toMap(ParticipantData::getId, Function.identity()));

        var historyData = group.getHistory().stream().map(history -> {
            return HistoryData.builder()
                    .group(groupData)
                    .fromParticipant(participantsMap.get(history.getFrom()))
                    .toParticipant(participantsMap.get(history.getTo()))
                    .value(history.getValue().getAmount())
                    .description(history.getDescription())
                    .build();
        }).toList();

        var summaryData = group.getDebtSummary().getSummary().entrySet().stream()
                .map(entry -> {
                    return SummaryData.builder()
                            .group(groupData)
                            .fromParticipant(participantsMap.get(entry.getKey().getValue0()))
                            .toParticipant(participantsMap.get(entry.getKey().getValue1()))
                            .value(entry.getValue().getAmount())
                            .build();
                })
                .toList();

        historyRepository.saveAll(historyData);
        summaryRepository.saveAll(summaryData);
        return true;
    }
}
