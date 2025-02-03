package com.nulstudio.nlp.domain.dto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record AgricultureProblemDto(
        boolean rejected,
        @NotNull List<Integer> answer
) {
}
