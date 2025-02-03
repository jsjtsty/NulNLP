package com.nulstudio.nlp.domain.cache;

import com.nulstudio.nlp.entity.NulCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CachedCategory implements NulCachedEntity<NulCategory> {
    private Long id;
    private String namespace;
    private Long categoryId;
    private String name;
    private String description;

    public CachedCategory() {}

    public CachedCategory(@Nullable NulCategory category) {
        if (category == null) return;
        id = category.getId();
        namespace = category.getNamespace();
        categoryId = category.getCategoryId();
        name = category.getName();
        description = category.getDescription();
    }

    @Override
    public @NotNull NulCategory restore() {
        final NulCategory category = new NulCategory();
        category.setId(id);
        category.setCategoryId(categoryId);
        category.setNamespace(namespace);
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
