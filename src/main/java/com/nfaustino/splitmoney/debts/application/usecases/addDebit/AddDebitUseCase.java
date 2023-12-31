package com.nfaustino.splitmoney.debts.application.usecases.addDebit;

import com.nfaustino.splitmoney.debts.application.services.GroupDebitService;
import com.nfaustino.splitmoney.debts.domain.Payment;
import com.nfaustino.splitmoney.debts.domain.exceptions.GroupNotFound;
import com.nfaustino.splitmoney.shared.base.UseCase;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddDebitUseCase extends UseCase<AddDebitInput, AddDebitOutput> {
    GroupDebitService groupDebitService;

    @Override
    public AddDebitOutput execute(AddDebitInput input) {
        var group = groupDebitService.getGroupById(input.groupId())
                .orElseThrow(() -> new GroupNotFound(input.groupId()));
        var payment = Payment.builder().from(input.from())
                .date(input.date())
                .value(input.value())
                .description(input.description())
                .build();
        if (input.participants() == null || input.participants().isEmpty())
            group.addPaymentSplitEqual(payment);
        else
            group.addPaymentSplitEqual(payment, input.participants());
        groupDebitService.save(group);
        return AddDebitOutput.builder().build();
    }

}
