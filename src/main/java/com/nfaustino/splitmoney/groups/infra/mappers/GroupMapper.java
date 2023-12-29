package com.nfaustino.splitmoney.groups.infra.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupInput;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.groups.infra.controller.CreateGroupRestInput;
import com.nfaustino.splitmoney.shared.infra.data.GroupData;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @Mapping(target = "participants", ignore = true)
    public Group fromGroupData(GroupData data);

    public CreateGroupInput fromCreateGroupResInput(CreateGroupRestInput input);
}
