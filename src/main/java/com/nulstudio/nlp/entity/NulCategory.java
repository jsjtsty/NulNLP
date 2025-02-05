package com.nulstudio.nlp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nul_category")
public class NulCategory {

    public static final String NAMESPACE_IMAGE_BENCHMARK = "image_benchmark";

    public static final String NAMESPACE_PROBLEM = "problem";

    public static final String NAMESPACE_IMAGE_PROBLEM = "image_problem";

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "namespace", nullable = false)
    private String namespace;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

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