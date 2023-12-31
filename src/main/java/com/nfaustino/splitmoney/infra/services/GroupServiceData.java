package com.nfaustino.splitmoney.infra.services;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nfaustino.splitmoney.groups.application.services.GroupService;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.infra.db.data.GroupData;
import com.nfaustino.splitmoney.infra.db.data.ParticipantData;
import com.nfaustino.splitmoney.infra.db.repositories.GroupRepository;
import com.nfaustino.splitmoney.infra.mappers.GroupMapper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class GroupServiceData implements GroupService {
    GroupRepository repository;
    GroupMapper mapper;

    @Override
    public Group save(Group group) {
        if (group.getId() == 0) {
            return create(group);
        }
        return update(group);
    }

    private Group create(Group group) {
        var data = GroupData.builder().name(group.getName()).build();
        var saved = repository.save(data);
        return mapper.fromGroupData(saved);
    }

    private Group update(Group group) {
        var groupData = GroupData.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
        groupData.setParticipants(group.getParticipants().stream().map(p -> ParticipantData.builder()
                .name(p.getName())
                .group(groupData)
                .build())
                .toList());
        var saved = repository.save(groupData);
        return mapper.fromGroupData(saved);
    }

    @Override
    @Transactional
    public Optional<Group> getGroupById(int id) {
        var result = repository.findById(id);
        return result.map(mapper::fromGroupData);
    }
}
