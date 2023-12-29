package com.nfaustino.splitmoney.groups.infra.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGroupRestInput {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 0, max = 255, message = "Name must have between 0 and 255")
    String name;
}
