package com.nfaustino.splitmoney.groups.infra.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantOutput;
import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantUseCase;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupOutput;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupUseCase;
import com.nfaustino.splitmoney.groups.infra.mappers.GroupMapper;
import com.nfaustino.splitmoney.shared.base.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "v1/group")
@AllArgsConstructor
public class GroupController {
    CreateGroupUseCase createGroupUseCase;
    AddParticipantUseCase addParticipantUseCase;
    GroupMapper mapper;

    @PostMapping()
    public ApiResponse<CreateGroupOutput> createGroup(@Valid @RequestBody CreateGroupRestInput data) {
        var input = mapper.fromCreateGroupResInput(data);
        var response = createGroupUseCase.execute(input);
        return ApiResponse.ok(response);
    }

    @PostMapping("/{groupId}/participant")
    public ApiResponse<AddParticipantOutput> addParticipant(@PathVariable int groupId,
            @Valid @RequestBody AddParticipantRestInput data) {
        var input = mapper.fromAddParticipantRestInput(groupId, data);
        var response = addParticipantUseCase.execute(input);
        return ApiResponse.ok(response);
    }

}
