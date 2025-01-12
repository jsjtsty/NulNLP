package com.nulstudio.nlp.domain.cache;

import com.nulstudio.nlp.entity.NulAgricultureImageBenchmark;
import org.jetbrains.annotations.NotNull;

public class CachedAgricultureImageBenchmark implements NulCachedEntity<NulAgricultureImageBenchmark> {
    private Long id;
    private long uid;
    private long entryId;
    private Boolean question;
    private Boolean options;
    private Boolean logic;
    private Boolean optionAnalysis;
    private Boolean answer;

    public CachedAgricultureImageBenchmark() {}

    public CachedAgricultureImageBenchmark(@NotNull NulAgricultureImageBenchmark entity) {
        this.id = entity.getId();
        this.uid = entity.getUid();
        this.entryId = entity.getEntryId();
        this.question = entity.getQuestion();
        this.options = entity.getOptions();
        this.logic = entity.getLogic();
        this.optionAnalysis = entity.getOptionAnalysis();
        this.answer = entity.getAnswer();
    }


    @Override
    @NotNull
    public NulAgricultureImageBenchmark restore() {
        final NulAgricultureImageBenchmark benchmark = new NulAgricultureImageBenchmark();
        benchmark.setId(id);
        benchmark.setUid(uid);
        benchmark.setEntryId(entryId);
        benchmark.setQuestion(question);
        benchmark.setOptions(options);
        benchmark.setLogic(logic);
        benchmark.setOptionAnalysis(optionAnalysis);
        benchmark.setAnswer(answer);
        return benchmark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }

    public Boolean getQuestion() {
        return question;
    }

    public void setQuestion(Boolean question) {
        this.question = question;
    }

    public Boolean getOptions() {
        return options;
    }

    public void setOptions(Boolean options) {
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
}
