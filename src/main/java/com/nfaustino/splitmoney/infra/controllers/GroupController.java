package com.nfaustino.splitmoney.infra.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nfaustino.splitmoney.debts.application.usecases.addDebit.AddDebitOutput;
import com.nfaustino.splitmoney.debts.application.usecases.addDebit.AddDebitUseCase;
import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantOutput;
import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantUseCase;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupOutput;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupUseCase;
import com.nfaustino.splitmoney.infra.controllers.dto.AddDebitRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.AddParticipantRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.CreateGroupRestInput;
import com.nfaustino.splitmoney.infra.controllers.dto.GroupDetails;
import com.nfaustino.splitmoney.infra.mappers.GroupDebitMapper;
import com.nfaustino.splitmoney.infra.mappers.GroupMapper;
import com.nfaustino.splitmoney.infra.queries.GroupQueries;
import com.nfaustino.splitmoney.shared.base.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "v1/group")
@AllArgsConstructor
public class GroupController {
    CreateGroupUseCase createGroupUseCase;
    AddParticipantUseCase addParticipantUseCase;
    AddDebitUseCase addDebitUseCase;
    GroupMapper mapper;
    GroupDebitMapper groupDebitMapper;
    GroupQueries groupService;

    @GetMapping("/{groupId}")
    public ApiResponse<GroupDetails> getMethodName(@PathVariable int groupId) {
        var result = groupService.getGroupDataById(groupId);
        return ApiResponse.ok(result);
    }

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

    @PostMapping("/{groupId}/payment")
    public ApiResponse<AddDebitOutput> addPayment(@PathVariable int groupId,
            @Valid @RequestBody AddDebitRestInput data) {
        var input = groupDebitMapper.map(groupId, data);
        var response = addDebitUseCase.execute(input);
        return ApiResponse.ok(response);
    }

}
