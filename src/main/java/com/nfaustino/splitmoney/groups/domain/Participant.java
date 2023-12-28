package com.nfaustino.splitmoney.groups.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Participant {
    int id;
    String name;

    void setName(String name) {
        this.name = name.trim();
    }

    static class ParticipantBuilder {
        String name;

        ParticipantBuilder name(String name) {
            this.name = name.trim();
            return this;
        }
    }
}
