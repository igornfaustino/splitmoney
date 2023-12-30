package com.nfaustino.splitmoney.queries.group.services;

import org.springframework.stereotype.Service;

import com.nfaustino.splitmoney.queries.group.dto.GroupDetails;
import com.nfaustino.splitmoney.queries.group.mappers.GroupQueryMapper;
import com.nfaustino.splitmoney.shared.base.exceptions.NotFound;
import com.nfaustino.splitmoney.shared.infra.repositories.GroupRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GroupService {
    GroupRepository groupRepository;
    GroupQueryMapper mapper;

    @Transactional
    public GroupDetails getGroupDataById(int id) {
        var result = groupRepository.findById(id);
        return result.map(mapper::groupDetailsFromGroupData)
                .orElseThrow(() -> new NotFound("Group not found with id %d".formatted(id)));
    }
}
