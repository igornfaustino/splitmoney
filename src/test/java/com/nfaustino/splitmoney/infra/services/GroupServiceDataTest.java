package com.nfaustino.splitmoney.infra.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.nfaustino.splitmoney.groups.domain.Group;
import com.nfaustino.splitmoney.groups.domain.Participant;
import com.nfaustino.splitmoney.utils.DatabaseTest;

import jakarta.transaction.Transactional;

@SpringBootTest
public class GroupServiceDataTest extends DatabaseTest {
    @Autowired
    GroupServiceData groupServiceData;

    @Test
    @Transactional
    public void should_saveNewGroup() {
        var result = groupServiceData.save(Group.builder().name("Teste").build());

        assertThat(result.getId()).isNotEqualTo(0);
        var savedGroup = groupServiceData.getGroupById(result.getId());
        assertThat(savedGroup.isPresent()).isTrue();
        assertThat(savedGroup.get().getName()).isEqualTo("Teste");
        assertThat(savedGroup.get().getId()).isEqualTo(result.getId());
    }

    @Test
    @Transactional
    public void should_updateAExistingGroup() {
        var result = groupServiceData.save(Group.builder().name("Teste").build());
        var savedGroup = groupServiceData.getGroupById(result.getId()).get();
        savedGroup.setName("Updated group");

        groupServiceData.save(savedGroup);

        var updatedGroup = groupServiceData.getGroupById(result.getId());
        assertThat(result.getId()).isNotEqualTo(0);
        assertThat(updatedGroup.isPresent()).isTrue();
        assertThat(updatedGroup.get().getName()).isEqualTo("Updated group");
        assertThat(updatedGroup.get().getId()).isEqualTo(result.getId());
    }

    @Test
    @DataSet(value = "valid-group.yml", cleanBefore = true, cleanAfter = true)
    public void should_SaveParticipants() {
        var savedGroup = groupServiceData.getGroupById(1000).get();
        savedGroup.addParticipant(Participant.builder().name("Carlos").build());

        groupServiceData.save(savedGroup);

        var updatedGroup = groupServiceData.getGroupById(1000);
        assertThat(updatedGroup.get().getParticipants()).isNotEmpty().hasSize(1);
        assertThat(updatedGroup.get().getParticipants().get(0).getName()).isEqualTo("Carlos");
    }
}
