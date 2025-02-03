package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.domain.cache.CachedAgricultureProblem;
import com.nulstudio.nlp.entity.NulAgricultureProblemDocument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class AgricultureProblemVo {
    private NulAgricultureProblemDocument document;
    private List<Integer> answer;
    private Boolean rejected;

    public AgricultureProblemVo(@NotNull NulAgricultureProblemDocument document) {
        this.document = document;
    }

    public AgricultureProblemVo(@NotNull NulAgricultureProblemDocument document,
                                @Nullable CachedAgricultureProblem entry) {
        this.document = document;
        if (entry == null) return;
        this.answer = entry.getAnswer();
        this.rejected = entry.getRejected();
    }

    public NulAgricultureProblemDocument getDocument() {
        return document;
    }

    public void setDocument(NulAgricultureProblemDocument document) {
        this.document = document;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }
}
