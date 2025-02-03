package com.nulstudio.nlp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nul_agriculture_image_benchmark")
public class NulAgricultureImageBenchmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "entry_id", nullable = false)
    private Long entryId;

    @Column(name = "question")
    private String question;

    @Column(name = "options")
    private String options;

    @Column(name = "logic")
    private Boolean logic;

    @Column(name = "option_analysis")
    private Boolean optionAnalysis;

    @Column(name = "answer")
    private Boolean answer;

    @Column(name = "category", nullable = false)
    private Long category;

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
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

}