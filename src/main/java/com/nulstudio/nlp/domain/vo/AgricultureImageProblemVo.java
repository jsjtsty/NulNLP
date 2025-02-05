package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageProblem;
import com.nulstudio.nlp.entity.NulAgricultureImageProblemDocument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AgricultureImageProblemVo {
    private NulAgricultureImageProblemDocument document;
    private Integer answer;
    private Boolean rejected;

    public AgricultureImageProblemVo(@NotNull NulAgricultureImageProblemDocument document) {
        this.document = document;
    }

    public AgricultureImageProblemVo(
            @NotNull NulAgricultureImageProblemDocument document,
            @Nullable CachedAgricultureImageProblem problem
    ) {
        this.document = document;
        if (problem == null) return;
        this.answer = problem.getAnswer();
        this.rejected = problem.getRejected();
    }

    public NulAgricultureImageProblemDocument getDocument() {
        return document;
    }

    public void setDocument(NulAgricultureImageProblemDocument document) {
        this.document = document;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }
}
