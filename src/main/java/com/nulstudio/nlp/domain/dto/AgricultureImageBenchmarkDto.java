package com.nulstudio.nlp.domain.dto;

import jakarta.annotation.Nullable;

public record AgricultureImageBenchmarkDto(
        @Nullable Boolean question,
        @Nullable Boolean options,
        @Nullable Boolean logic,
        @Nullable Boolean optionAnalysis,
        @Nullable Boolean answer
) {

}
