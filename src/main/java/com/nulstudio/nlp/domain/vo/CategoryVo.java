package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.domain.cache.CachedCategory;
import org.jetbrains.annotations.Nullable;

public final class CategoryVo {
    private Long id;
    private String name;
    private String description;

    public CategoryVo() {}

    public CategoryVo(@Nullable CachedCategory category) {
        if (category == null) return;
        id = category.getCategoryId();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
