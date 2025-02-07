package com.nulstudio.nlp.domain.vo;

import org.bson.Document;

public final class DocumentAnnotationVo {
    private Document document;
    private Document annotation;

    public DocumentAnnotationVo(Document document, Document annotation) {
        this.document = document;
        this.annotation = annotation;
    }

    public DocumentAnnotationVo() {}

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Document annotation) {
        this.annotation = annotation;
    }
}
