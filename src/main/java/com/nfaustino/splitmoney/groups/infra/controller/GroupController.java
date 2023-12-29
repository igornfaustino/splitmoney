package com.nfaustino.splitmoney.groups.infra.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupInput;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupOutput;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupUseCase;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "v1/group")
@AllArgsConstructor
public class GroupController {
    CreateGroupUseCase createGroupUseCase;

    @PostMapping()
    public CreateGroupOutput createGroup(@Valid @RequestBody CreateGroupRestInput data) {
        var input = CreateGroupInput.builder().name(data.getName()).build();
        var response = createGroupUseCase.execute(input);
        return response;
    }
}
