package com.nfaustino.splitmoney.debts.domain.exceptions;

import com.nfaustino.splitmoney.shared.exceptions.BadRequest;

public class GroupNotFound extends BadRequest {
    public GroupNotFound(int group) {
        super("Group with id %d was not found".formatted(group));
    }
}
