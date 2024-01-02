package com.nfaustino.splitmoney.infra.mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.nfaustino.splitmoney.debts.application.usecases.addDebit.AddDebitInput;
import com.nfaustino.splitmoney.debts.domain.DebtSummary;
import com.nfaustino.splitmoney.debts.domain.Group;
import com.nfaustino.splitmoney.debts.domain.Transaction;
import com.nfaustino.splitmoney.infra.controllers.dto.AddDebitRestInput;
import com.nfaustino.splitmoney.infra.db.data.GroupData;
import com.nfaustino.splitmoney.infra.db.data.HistoryData;
import com.nfaustino.splitmoney.infra.db.data.SummaryData;
import com.nfaustino.splitmoney.shared.valueobjects.Money;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public interface GroupDebitMapper {
    @Mapping(target = "history", ignore = true)
    @Mapping(target = "debtSummary", source = "summary", qualifiedByName = "SummaryDataToSummary")
    public Group fromGroupData(GroupData data);

    List<Transaction> map(List<HistoryData> historyDataList);

    @Mapping(target = "from", source = "historyData.fromParticipant.id")
    @Mapping(target = "to", source = "historyData.toParticipant.id")
    @Mapping(target = "value", qualifiedByName = "BigDecimalToMoney")
    @Mapping(target = "createDate", source = "historyData.transactionDate")
    Transaction map(HistoryData historyData);

    @Named("BigDecimalToMoney")
    default Money mapValue(BigDecimal value) {
        return Money.real(value);
    }

    @Named("SummaryDataToSummary")
    default DebtSummary fromSummaryList(List<SummaryData> summaryList) {
        var map = summaryList.stream()
                .collect(Collectors.toMap(
                        summary -> Pair.with(summary.getFromParticipant().getId(), summary.getToParticipant().getId()),
                        summary -> Money.real(summary.getValue())));
        return DebtSummary.builder().summary(map).build();
    }

    AddDebitInput map(int groupId, AddDebitRestInput map);
}
