package com.nulstudio.nlp.domain.dto;

import jakarta.annotation.Nullable;

public record AgricultureImageBenchmarkDto(
        @Nullable String question,
        @Nullable String options,
        @Nullable Boolean logic,
        @Nullable Boolean optionAnalysis,
        @Nullable Boolean answer
) {

}
