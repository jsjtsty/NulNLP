package com.nulstudio.nlp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("nul_document_schema")
public class NulDocumentSchema {
    @Id
    public String id;

    @Field(value = "namespace")
    public String namespace;

    @Field(value = "name")
    public String name;

    @Field(value = "annotation_schema")
    public String annotationSchema;

    @Field(value = "categories")
    public List<NulCategory> categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NulCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<NulCategory> categories) {
        this.categories = categories;
    }

    public String getAnnotationSchema() {
        return annotationSchema;
    }

    public void setAnnotationSchema(String annotationSchema) {
        this.annotationSchema = annotationSchema;
    }
}
