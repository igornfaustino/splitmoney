package com.nfaustino.splitmoney.infra.mappers;

import org.mapstruct.Mapper;

import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantInput;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupInput;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.infra.controllers.dto.AddParticipantRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.CreateGroupRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.GroupDetails;
import com.nfaustino.splitmoney.infra.db.data.GroupData;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    public Group fromGroupData(GroupData data);

    public CreateGroupInput fromCreateGroupResInput(CreateGroupRestInput input);

    public AddParticipantInput fromAddParticipantRestInput(int groupId, AddParticipantRestInput rest);

    public GroupDetails groupDetailsFromGroupData(GroupData data);
}
