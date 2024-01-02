package com.nfaustino.splitmoney.infra.mappers;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantInput;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupInput;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.infra.controllers.dto.AddParticipantRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.CreateGroupRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.GroupDetails;
import com.nfaustino.splitmoney.infra.controllers.dto.HistoryDetails;
import com.nfaustino.splitmoney.infra.controllers.dto.SummaryDetails;
import com.nfaustino.splitmoney.infra.db.data.GroupData;
import com.nfaustino.splitmoney.infra.db.data.HistoryData;
import com.nfaustino.splitmoney.infra.db.data.SummaryData;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public interface GroupMapper {
    public Group fromGroupData(GroupData data);

    public CreateGroupInput fromCreateGroupResInput(CreateGroupRestInput input);

    public AddParticipantInput fromAddParticipantRestInput(int groupId, AddParticipantRestInput rest);

    @Mapping(target = "groupHistory", source = "history")
    public GroupDetails groupDetailsFromGroupData(GroupData data);

    @Mapping(target = "from", source = "fromParticipant.id")
    @Mapping(target = "to", source = "toParticipant.id")
    @Mapping(target = "date", source = "transactionDate")
    public HistoryDetails map(HistoryData history);

    @Mapping(target = "from", source = "fromParticipant.id")
    @Mapping(target = "to", source = "toParticipant.id")
    public SummaryDetails map(SummaryData history);
}
