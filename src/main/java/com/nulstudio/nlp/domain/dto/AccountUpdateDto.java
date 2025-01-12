package com.nulstudio.nlp.domain.dto;

import org.jetbrains.annotations.Nullable;

public record AccountUpdateDto(
        @Nullable String password,
        @Nullable String nickName
) {
}
