package com.nfaustino.splitmoney.groups.infra.service;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nfaustino.splitmoney.groups.application.service.GroupService;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.groups.infra.mappers.GroupMapper;
import com.nfaustino.splitmoney.shared.infra.data.GroupData;
import com.nfaustino.splitmoney.shared.infra.repositories.GroupRepository;

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
        return group;
    }

    private Group create(Group group) {
        var data = GroupData.builder().name(group.getName()).build();
        var saved = repository.save(data);
        return mapper.fromGroupData(saved);
    }

    @Override
    public Optional<Group> getGroupById(int id) {
        var result = repository.findById(id);
        return result.map(mapper::fromGroupData);
    }
}