package com.nfaustino.splitmoney.groups.domain.exceptions;

import com.nfaustino.splitmoney.shared.base.exceptions.BadRequest;

public class GroupNotFound extends BadRequest {
    public GroupNotFound(int group) {
        super("Group with id %d was not found".formatted(group));
    }
}
