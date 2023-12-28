package com.nfaustino.splitmoney.groups.application.usecases.AddParticipant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nfaustino.splitmoney.groups.application.service.GroupService;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.groups.domain.Participant;
import com.nfaustino.splitmoney.groups.domain.exceptions.DuplicatedParticipant;

@ExtendWith(MockitoExtension.class)
public class AddParticipantUseCaseTest {
    @InjectMocks
    AddParticipantUseCase useCase;

    @Mock
    GroupService groupService;

    @Captor
    ArgumentCaptor<Group> groupCaptor;

    @Captor
    ArgumentCaptor<Integer> groupIdCaptor;

    @Test
    void should_AddNewParticipantToGroup() {
        when(groupService.getGroupById(groupIdCaptor.capture()))
                .thenReturn(Optional.of(Group.builder().id(1).name("test").build()));
        when(groupService.save(groupCaptor.capture())).thenReturn(null);
        var input = AddParticipantInput.builder().groupId(1).name("John Doo").build();

        useCase.execute(input);

        assertThat(groupIdCaptor.getValue()).isEqualTo(1);
        assertThat(groupCaptor.getValue().getParticipants()).isNotEmpty()
                .contains(Participant.builder().name("John Doo").build());

    }

    @Test
    void should_NOTAddNewParticipantsWithSameNameToGroup() {
        when(groupService.getGroupById(groupIdCaptor.capture()))
                .thenReturn(Optional.of(Group.builder()
                        .id(1)
                        .name("test")
                        .participants(List.of(Participant.builder().id(1).name("John Doo").build()))
                        .build()));
        var input = AddParticipantInput.builder().groupId(1).name("John Doo").build();

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(DuplicatedParticipant.class)
                .hasMessage("Participant with name John Doo already exists");
    }
}
