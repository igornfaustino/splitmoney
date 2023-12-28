package com.nfaustino.splitmoney.groups.application.usecases.CreateGroup;

import lombok.Builder;

@Builder
public record CreateGroupOutput(int id, String name) {

}
