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
        if (name == null)
            return;
        this.name = name.trim();
    }

    public static class ParticipantBuilder {
        String name;

        public ParticipantBuilder name(String name) {
            if (name == null)
                return this;
            this.name = name.trim();
            return this;
        }
    }
}
