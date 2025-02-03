package com.nulstudio.nlp.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(value = "nul_agriculture_image_benchmark")
public class NulAgricultureImageBenchmarkDocument {
    @Field(value = "id")
    public Long id;

    @Field(value = "category")
    public Long category;

    @Field(value = "answer")
    public Integer answer;

    @Field(value = "aspect")
    public String aspect;

    @Field(value = "aspect_knowledge")
    public String aspectKnowledge;

    @Field(value = "background_knowledge")
    public String backgroundKnowledge;

    @Field(value = "crop_name")
    public String cropName;

    @Field(value = "disease_name")
    public String diseaseName;

    @Field(value = "disease_type")
    public String diseaseType;

    @Field(value = "image")
    public String image;

    @Field(value = "options")
    public List<String> options;

    @Field(value = "options_analysis")
    public List<String> optionsAnalysis;

    @Field(value = "question")
    public String question;

    @Field(value = "question_id")
    public String questionId;

    @Field(value = "solving_logic")
    public List<String> solvingLogic;

    @Field(value = "uuid")
    public String uuid;

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getAspectKnowledge() {
        return aspectKnowledge;
    }

    public void setAspectKnowledge(String aspectKnowledge) {
        this.aspectKnowledge = aspectKnowledge;
    }

    public String getBackgroundKnowledge() {
        return backgroundKnowledge;
    }

    public void setBackgroundKnowledge(String backgroundKnowledge) {
        this.backgroundKnowledge = backgroundKnowledge;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getOptionsAnalysis() {
        return optionsAnalysis;
    }

    public void setOptionsAnalysis(List<String> optionsAnalysis) {
        this.optionsAnalysis = optionsAnalysis;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<String> getSolvingLogic() {
        return solvingLogic;
    }

    public void setSolvingLogic(List<String> solvingLogic) {
        this.solvingLogic = solvingLogic;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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