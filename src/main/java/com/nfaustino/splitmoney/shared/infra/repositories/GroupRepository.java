package com.nfaustino.splitmoney.shared.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nfaustino.splitmoney.shared.infra.data.GroupData;

public interface GroupRepository extends JpaRepository<GroupData, Integer> {

}
