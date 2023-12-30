package com.nfaustino.splitmoney.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nfaustino.splitmoney.groups.application.usecases.AddParticipant.AddParticipantUseCase;
import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupUseCase;
import com.nfaustino.splitmoney.infra.services.GroupServiceData;

@Configuration
public class SpringConfig {
    @Bean
    public CreateGroupUseCase createGroupUseCase(GroupServiceData groupService) {
        return new CreateGroupUseCase(groupService);
    }

    @Bean
    public AddParticipantUseCase addParticipantUseCase(GroupServiceData groupService) {
        return new AddParticipantUseCase(groupService);
    }
}
