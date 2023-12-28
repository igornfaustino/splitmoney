package com.nfaustino.splitmoney.groups.application.usecases.AddParticipant;

import com.nfaustino.splitmoney.groups.application.service.GroupService;
import com.nfaustino.splitmoney.groups.domain.Participant;
import com.nfaustino.splitmoney.groups.domain.exceptions.GroupNotFound;
import com.nfaustino.splitmoney.shared.base.UseCase;

public class AddParticipantUseCase extends UseCase<AddParticipantInput, AddParticipantOutput> {
    GroupService groupService;

    public AddParticipantUseCase(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public AddParticipantOutput execute(AddParticipantInput input) {
        var group = groupService.getGroupById(input.groupId()).orElseThrow(() -> new GroupNotFound(input.groupId()));
        var newParticipant = Participant.builder().name(input.name()).build();
        group.addParticipant(newParticipant);
        groupService.save(group);
        return new AddParticipantOutput();
    }

}
