package com.nfaustino.splitmoney.infra.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nfaustino.splitmoney.infra.db.data.SummaryData;
import com.nfaustino.splitmoney.infra.db.data.SummaryDataId;

public interface SummaryRepository extends JpaRepository<SummaryData, SummaryDataId> {

}
