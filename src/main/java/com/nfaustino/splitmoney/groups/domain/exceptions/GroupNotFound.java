package com.nfaustino.splitmoney.groups.domain.exceptions;

public class GroupNotFound extends RuntimeException {
    public GroupNotFound(int group) {
        super("Group with id %d was not found".formatted(group));
    }
}
