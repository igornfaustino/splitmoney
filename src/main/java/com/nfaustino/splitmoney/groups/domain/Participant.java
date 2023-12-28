package com.nfaustino.splitmoney.groups.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Participant {
    int id;
    String name;
}
