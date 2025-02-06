package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.entity.NulCategory;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.Nullable;

public final class CategoryVo {
    private ObjectId id;
    private String categoryId;
    private String name;
    private String description;

    public CategoryVo() {}

    public CategoryVo(@Nullable NulCategory category) {
        if (category == null) return;
        id = category.getId();
        categoryId = category.getCategoryId();
        name = category.getName();
        description = category.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
