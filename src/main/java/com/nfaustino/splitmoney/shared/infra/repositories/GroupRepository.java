package com.nfaustino.splitmoney.shared.infra.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nfaustino.splitmoney.shared.infra.data.GroupData;

public interface GroupRepository extends CrudRepository<GroupData, Integer> {

}
