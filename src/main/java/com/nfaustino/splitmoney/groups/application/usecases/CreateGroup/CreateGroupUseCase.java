package com.nfaustino.splitmoney.groups.application.usecases.CreateGroup;

import com.nfaustino.splitmoney.groups.application.service.GroupService;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.shared.base.UseCase;

public class CreateGroupUseCase extends UseCase<CreateGroupInput, CreateGroupOutput> {
    GroupService groupService;

    public CreateGroupUseCase(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public CreateGroupOutput execute(CreateGroupInput input) {
        var group = Group.builder().name(input.name()).build();
        var savedGroup = groupService.save(group);
        return CreateGroupOutput.builder()
                .id(savedGroup.getId())
                .name(savedGroup.getName())
                .build();
    }

}
