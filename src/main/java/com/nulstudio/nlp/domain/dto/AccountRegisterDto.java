package com.nulstudio.nlp.domain.dto;

import org.jetbrains.annotations.NotNull;

public record AccountRegisterDto(
        @NotNull String username,
        @NotNull String password,
        @NotNull String name,
        @NotNull String inviteCode
) {
}
