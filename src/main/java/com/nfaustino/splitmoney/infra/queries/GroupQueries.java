package com.nfaustino.splitmoney.infra.queries;

import org.springframework.stereotype.Service;

import com.nfaustino.splitmoney.infra.controllers.dto.GroupDetails;
import com.nfaustino.splitmoney.infra.db.repositories.GroupRepository;
import com.nfaustino.splitmoney.infra.mappers.GroupMapper;
import com.nfaustino.splitmoney.shared.exceptions.NotFound;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GroupQueries {
    GroupRepository groupRepository;
    GroupMapper mapper;

    @Transactional
    public GroupDetails getGroupDataById(int id) {
        var result = groupRepository.findById(id);
        return result.map(mapper::groupDetailsFromGroupData)
                .orElseThrow(() -> new NotFound("Group not found with id %d".formatted(id)));
    }
}
