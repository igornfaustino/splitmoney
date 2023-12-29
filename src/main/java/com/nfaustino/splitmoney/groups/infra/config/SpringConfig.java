package com.nfaustino.splitmoney.groups.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nfaustino.splitmoney.groups.application.usecases.CreateGroup.CreateGroupUseCase;
import com.nfaustino.splitmoney.groups.infra.service.GroupServiceData;

@Configuration
public class SpringConfig {
    @Bean
    public CreateGroupUseCase createGroupUseCase(GroupServiceData groupService) {
        return new CreateGroupUseCase(groupService);
    }
}
