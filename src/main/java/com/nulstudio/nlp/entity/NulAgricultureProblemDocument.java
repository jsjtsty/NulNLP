package com.nulstudio.nlp.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(value = "nul_agriculture_problem")
public class NulAgricultureProblemDocument {
    @Field(value = "id")
    public Long id;

    @Field(value = "category")
    public Long category;

    @Field(value = "origin_id")
    public Long originId;

    @Field(value = "class_task")
    public String classTask;

    @Field(value = "domain_subtype")
    public String domainSubtype;

    @Field(value = "options")
    public List<String> options;

    @Field(value = "question")
    public String question;

    @Field(value = "question_type")
    public String questionType;

    @Field(value = "type")
    public String type;

    @Field(value = "class_type")
    public String classType;

    @Field(value = "domain_type")
    public String domainType;

    @Field(value = "gold_answer")
    public List<Integer> goldAnswer;

    public String getClassTask() {
        return classTask;
    }

    public void setClassTask(String classTask) {
        this.classTask = classTask;
    }

    public String getDomainSubtype() {
        return domainSubtype;
    }

    public void setDomainSubtype(String domainSubtype) {
        this.domainSubtype = domainSubtype;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public List<Integer> getGoldAnswer() {
        return goldAnswer;
    }

    public void setGoldAnswer(List<Integer> goldAnswer) {
        this.goldAnswer = goldAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

}