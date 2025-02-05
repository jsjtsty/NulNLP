package com.nulstudio.nlp.domain.dto;

import org.jetbrains.annotations.Nullable;

public record AgricultureImageProblemDto(
        boolean rejected,
        @Nullable Integer answer
) {
}
