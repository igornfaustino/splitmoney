package com.nfaustino.splitmoney.owes.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Participant {
    int id;
    String name;
}
