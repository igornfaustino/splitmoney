package com.nfaustino.splitmoney.groups.application.usecases.CreateGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nfaustino.splitmoney.groups.application.service.GroupService;
import com.nfaustino.splitmoney.groups.domain.Group;

@ExtendWith(MockitoExtension.class)
public class CreateGroupUseCaseTest {
    @InjectMocks
    CreateGroupUseCase useCase;

    @Mock
    GroupService groupService;

    @Captor
    ArgumentCaptor<Group> groupCaptor;

    @Test
    void should_CreateANewGroup() {
        var input = new CreateGroupInput("test");
        when(groupService.save(groupCaptor.capture())).thenReturn(Group.builder().id(1).name("test").build());

        var output = useCase.execute(input);

        assertThat(output.id()).isEqualTo(1);
        assertThat(output.name()).isEqualTo("test");
        assertThat(groupCaptor.getValue()).isEqualTo(Group.builder().name("test").build());
    }

}
