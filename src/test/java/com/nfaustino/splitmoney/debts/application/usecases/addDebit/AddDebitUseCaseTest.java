package com.nfaustino.splitmoney.debts.application.usecases.addDebit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nfaustino.splitmoney.debts.application.services.GroupDebitService;
import com.nfaustino.splitmoney.debts.domain.Group;
import com.nfaustino.splitmoney.debts.domain.Participant;
import com.nfaustino.splitmoney.shared.valueobjects.Money;

@ExtendWith(MockitoExtension.class)
public class AddDebitUseCaseTest {
    @InjectMocks
    AddDebitUseCase useCase;

    @Mock
    GroupDebitService groupDebitService;

    @Captor
    ArgumentCaptor<Group> groupCaptor;

    @Test
    void ShouldSplitPaymentBetweenEveryParticipantWhenNoParticipantsProvided() {
        when(groupDebitService.getGroupById(anyInt())).thenReturn(Optional.of(createGroup(3)));
        when(groupDebitService.saveDebts(groupCaptor.capture())).thenReturn(null);
        var input = AddDebitInput.builder()
                .from(1)
                .value(BigDecimal.TEN)
                .groupId(1)
                .build();

        useCase.execute(input);

        assertThat(groupCaptor.getValue().getHistory()).hasSize(2);
        assertThat(groupCaptor.getValue().getHistory().getFirst().getValue()).isEqualTo(Money.real(3.33));
    }

    @Test
    void ShouldSplitPaymentBetweenEveryParticipantWhenParticipantsIsEmpty() {
        when(groupDebitService.getGroupById(anyInt())).thenReturn(Optional.of(createGroup(3)));
        when(groupDebitService.saveDebts(groupCaptor.capture())).thenReturn(null);
        var input = AddDebitInput.builder()
                .from(1)
                .value(BigDecimal.TEN)
                .groupId(1)
                .participants(List.of())
                .build();

        useCase.execute(input);

        assertThat(groupCaptor.getValue().getHistory()).hasSize(2);
        assertThat(groupCaptor.getValue().getHistory().getFirst().getValue()).isEqualTo(Money.real(3.33));
    }

    @Test
    void ShouldSplitPaymentBetweenEveryProvidedParticipant() {
        when(groupDebitService.getGroupById(anyInt())).thenReturn(Optional.of(createGroup(3)));
        when(groupDebitService.saveDebts(groupCaptor.capture())).thenReturn(null);
        var input = AddDebitInput.builder()
                .from(1)
                .value(BigDecimal.TEN)
                .groupId(1)
                .participants(List.of(2))
                .build();

        useCase.execute(input);

        assertThat(groupCaptor.getValue().getHistory()).hasSize(1);
        assertThat(groupCaptor.getValue().getHistory().getFirst().getValue())
                .isEqualTo(Money.real(5));
    }

    @Test
    void ShouldReturnError_WhenGroupNotFound() {
        when(groupDebitService.getGroupById(anyInt())).thenReturn(Optional.empty());
        var input = AddDebitInput.builder()
                .from(0)
                .value(BigDecimal.TEN)
                .groupId(1)
                .build();

        assertThatThrownBy(() -> useCase.execute(input)).hasMessage("Group with id 1 was not found");
    }

    private Group createGroup(int nParticipants) {
        var group = Group.builder().id(1).build();
        var participants = createParticipants(nParticipants);
        group.setParticipants(participants);
        return group;
    }

    List<Participant> createParticipants(int quantity) {
        List<Participant> participants = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            participants.add(Participant.builder().id(i + 1).build());
        }
        return participants;
    }
}
