package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.entity.NulDocumentSchema;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class DocumentSchemaVo {
    private String documentId;
    private String name;
    private String annotationSchema;
    private List<CategoryVo> categories;

    public DocumentSchemaVo() {}

    public DocumentSchemaVo(@NotNull NulDocumentSchema documentSchema) {
        this.documentId = documentSchema.getNamespace();
        this.name = documentSchema.getName();
        this.annotationSchema = documentSchema.getAnnotationSchema();
        this.categories = documentSchema.getCategories().stream()
                .map(CategoryVo::new).toList();
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotationSchema() {
        return annotationSchema;
    }

    public void setAnnotationSchema(String annotationSchema) {
        this.annotationSchema = annotationSchema;
    }

    public List<CategoryVo> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryVo> categories) {
        this.categories = categories;
    }
}
