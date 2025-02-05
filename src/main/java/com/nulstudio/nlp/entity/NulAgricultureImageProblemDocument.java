package com.nulstudio.nlp.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(value = "nul_agriculture_image_problem")
public class NulAgricultureImageProblemDocument {
    @Field(value = "id")
    public Long id;

    @Field(value = "category")
    public Long category;

    @Field(value = "image_id")
    public String imageId;

    @Field(value = "question")
    public String question;

    @Field(value = "image_path")
    public String imagePath;

    @Field(value = "options")
    public List<String> options;

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
