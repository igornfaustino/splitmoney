package com.nfaustino.splitmoney.queries.group.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nfaustino.splitmoney.queries.group.dto.GroupDetails;
import com.nfaustino.splitmoney.queries.group.services.GroupService;
import com.nfaustino.splitmoney.shared.base.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "v1/group")
@AllArgsConstructor
public class GroupQueryController {
    GroupService groupService;

    @GetMapping("/{groupId}")
    public ApiResponse<GroupDetails> getMethodName(@PathVariable int groupId) {
        var result = groupService.getGroupDataById(groupId);
        return ApiResponse.ok(result);
    }
}
