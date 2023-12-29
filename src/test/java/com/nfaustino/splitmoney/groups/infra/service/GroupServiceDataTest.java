package com.nfaustino.splitmoney.groups.infra.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.shared.infra.repositories.GroupRepository;

@SpringBootTest
public class GroupServiceDataTest {
    @Autowired
    GroupServiceData groupServiceData;

    @Autowired
    GroupRepository groupRepository;

    @Test
    public void should_saveNewGroup() {
        var result = groupServiceData.save(Group.builder().name("Teste").build());

        assertThat(result.getId()).isNotEqualTo(0);
        var savedGroup = groupServiceData.getGroupById(result.getId());
        assertThat(savedGroup.isPresent()).isTrue();
        assertThat(savedGroup.get().getName()).isEqualTo("Teste");
        assertThat(savedGroup.get().getId()).isEqualTo(result.getId());
    }
}
