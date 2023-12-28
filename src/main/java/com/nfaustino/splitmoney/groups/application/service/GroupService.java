package com.nfaustino.splitmoney.groups.application.service;

import java.util.Optional;

import com.nfaustino.splitmoney.groups.domain.Group;

public interface GroupService {
    public Group save(Group group);

    public Optional<Group> getGroupById(int id);
}
