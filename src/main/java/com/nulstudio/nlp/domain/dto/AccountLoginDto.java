package com.nulstudio.nlp.domain.dto;

import org.jetbrains.annotations.NotNull;

public record AccountLoginDto(
        @NotNull String username,
        @NotNull String password
) {
}
