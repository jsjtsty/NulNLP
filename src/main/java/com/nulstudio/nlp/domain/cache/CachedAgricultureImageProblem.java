package com.nulstudio.nlp.domain.cache;

import com.nulstudio.nlp.entity.NulAgricultureImageProblem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CachedAgricultureImageProblem implements NulCachedEntity<NulAgricultureImageProblem> {
    private String objectId;
    private Long uid;
    private Long category;
    private Long id;
    private Integer answer;
    private Boolean rejected;

    public CachedAgricultureImageProblem() {}

    public CachedAgricultureImageProblem(@Nullable NulAgricultureImageProblem problem) {
        if (problem == null) return;
        this.objectId = problem.getObjectId();
        this.uid = problem.getUid();
        this.category = problem.getCategory();
        this.id = problem.getId();
        this.answer = problem.getAnswer();
        this.rejected = problem.getRejected();
    }

    @Override
    public @NotNull NulAgricultureImageProblem restore() {
        final NulAgricultureImageProblem result = new NulAgricultureImageProblem();
        result.setObjectId(objectId);
        result.setUid(uid);
        result.setCategory(category);
        result.setId(id);
        result.setAnswer(answer);
        result.setRejected(rejected);
        return result;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
