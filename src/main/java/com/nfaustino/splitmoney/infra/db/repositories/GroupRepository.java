package com.nfaustino.splitmoney.infra.db.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nfaustino.splitmoney.infra.db.data.GroupData;

public interface GroupRepository extends CrudRepository<GroupData, Integer> {

}
