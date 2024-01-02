package com.nfaustino.splitmoney.infra.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nfaustino.splitmoney.infra.db.data.HistoryData;

public interface HistoryRepository extends JpaRepository<HistoryData, Integer> {
    List<HistoryData> findByGroupId(int groupId);
}
