package com.nulstudio.nlp.domain.cache;

import com.nulstudio.nlp.entity.NulAgricultureProblem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CachedAgricultureProblem implements NulCachedEntity<NulAgricultureProblem> {
    private String objectId;
    private Long uid;
    private Long category;
    private Long id;
    private List<Integer> answer;
    private Boolean rejected;

    public CachedAgricultureProblem() {}

    public CachedAgricultureProblem(@Nullable NulAgricultureProblem problem) {
        if (problem == null) return;
        this.objectId = problem.getObjectId();
        this.uid = problem.getUid();
        this.category = problem.getCategory();
        this.id = problem.getId();
        this.answer = problem.getAnswer();
        this.rejected = problem.getRejected();
    }

    @Override
    public @NotNull NulAgricultureProblem restore() {
        final NulAgricultureProblem problem = new NulAgricultureProblem();
        problem.setObjectId(this.objectId);
        problem.setUid(uid);
        problem.setCategory(category);
        problem.setId(id);
        problem.setAnswer(answer);
        problem.setRejected(rejected);
        return problem;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
