package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageBenchmark;
import com.nulstudio.nlp.entity.NulAgricultureImageBenchmarkDocument;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public final class AgricultureImageBenchmarkVo {
    private NulAgricultureImageBenchmarkDocument document;
    private String question;
    private String options;
    private Boolean logic;
    private Boolean optionAnalysis;
    private Boolean answer;

    public AgricultureImageBenchmarkVo() {}

    public AgricultureImageBenchmarkVo(@NotNull NulAgricultureImageBenchmarkDocument document) {
        this.document = document;
    }

    public AgricultureImageBenchmarkVo(@NotNull NulAgricultureImageBenchmarkDocument document,
                                       @Nullable CachedAgricultureImageBenchmark benchmark) {
        this.document = document;
        if (benchmark == null) return;
        this.question = benchmark.getQuestion();
        this.options = benchmark.getOptions();
        this.logic = benchmark.getLogic();
        this.optionAnalysis = benchmark.getOptionAnalysis();
        this.answer = benchmark.getAnswer();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Boolean getLogic() {
        return logic;
    }

    public void setLogic(Boolean logic) {
        this.logic = logic;
    }

    public Boolean getOptionAnalysis() {
        return optionAnalysis;
    }

    public void setOptionAnalysis(Boolean optionAnalysis) {
        this.optionAnalysis = optionAnalysis;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public NulAgricultureImageBenchmarkDocument getDocument() {
        return document;
    }

    public void setDocument(NulAgricultureImageBenchmarkDocument document) {
        this.document = document;
    }
}
