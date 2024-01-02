package com.nfaustino.splitmoney.debts.application.services;

import java.util.Optional;

import com.nfaustino.splitmoney.debts.domain.Group;

public interface GroupDebitService {
    public Optional<Group> getGroupById(int id);

    public boolean saveSummaryAndHistory(Group group);
}
