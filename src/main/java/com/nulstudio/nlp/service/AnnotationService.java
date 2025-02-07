package com.nulstudio.nlp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.lang.Nullable;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.nulstudio.nlp.config.NulSecurityConfig;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.domain.vo.DocumentAnnotationVo;
import com.nulstudio.nlp.domain.vo.DocumentSchemaVo;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.repository.DocumentSchemaRepository;
import jakarta.annotation.Resource;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AnnotationService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private DocumentSchemaRepository documentSchemaRepository;

    private final Cache<String, JsonSchema> schemaCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    private static final String FIELD_ID = "_id";
    private static final String FIELD_DOCUMENT = "document";
    private static final String FIELD_DOCUMENT_NAMESPACE = "namespace";
    private static final String FIELD_DOCUMENT_CATEGORY_ID = "category_id";
    private static final String FIELD_ANNOTATIONS_DOCUMENT_ID = "document_id";
    private static final String FIELD_ANNOTATIONS_UID = "uid";
    private static final String FIELD_ANNOTATIONS = "annotations";
    private static final String FIELD_SCHEMA_NAMESPACE = "namespace";
    private static final String FIELD_SCHEMA_ANNOTATION_SCHEMA = "annotation_schema";

    private static final String COLLECTION_DOCUMENT = "nul_document";
    private static final String COLLECTION_ANNOTATIONS = "nul_annotations";
    private static final String COLLECTION_SCHEMA = "nul_document_schema";

    public @NotNull Document findDocument(@NotNull ObjectId objectId) {
        final Query query = Query.query(Criteria.where(FIELD_ID).is(objectId));
        query.fields().include(FIELD_DOCUMENT);
        final Document document = mongoTemplate.findById(query, Document.class, COLLECTION_DOCUMENT);
        if (document == null) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }
        final Document documentContent = (Document) document.get(FIELD_DOCUMENT);
        if (documentContent == null) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }
        return documentContent;
    }

    public @Nullable Document findAnnotation(@NotNull ObjectId objectId) {
        final ObjectId uid = NulSecurityConfig.getContextAccount().getId();
        final Query query = new Query(
                Criteria.where(FIELD_ANNOTATIONS_DOCUMENT_ID).is(objectId)
                        .and(FIELD_ANNOTATIONS_UID).is(uid)
        );
        final Document document = mongoTemplate.findOne(query, Document.class, COLLECTION_ANNOTATIONS);
        if (document == null) {
            return null;
        }
        return (Document) document.get(FIELD_ANNOTATIONS);
    }

    public void updateAnnotation(@NotNull ObjectId objectId, @NotNull JsonNode annotations) {
        final ObjectId uid = NulSecurityConfig.getContextAccount().getId();

        final Query queryDocument = new Query(Criteria.where(FIELD_ID).is(objectId));
        queryDocument.fields().include(FIELD_DOCUMENT_NAMESPACE);
        final Document document = mongoTemplate.findOne(queryDocument, Document.class, COLLECTION_DOCUMENT);
        if (document == null) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }
        final String namespace = document.getString(FIELD_DOCUMENT_NAMESPACE);
        final JsonSchema schema = getSchemaValidator(namespace);
        final Set<ValidationMessage> validationMessages;
        validationMessages = schema.validate(annotations);
        if (!validationMessages.isEmpty()) {
            throw new NulException(NulExceptionConstants.ANNOTATION_WRONG_SYNTAX);
        }

        final Query queryLegacy = new Query(
                Criteria.where(FIELD_ANNOTATIONS_DOCUMENT_ID).is(objectId)
                        .and(FIELD_ANNOTATIONS_UID).is(uid)
        );
        queryLegacy.fields().include(FIELD_ID);
        final Update update = new Update()
                .setOnInsert(FIELD_ANNOTATIONS_DOCUMENT_ID, objectId)
                .setOnInsert(FIELD_ANNOTATIONS_UID, uid)
                .set(FIELD_ANNOTATIONS, annotations);
        mongoTemplate.upsert(queryLegacy, update, COLLECTION_ANNOTATIONS);
    }

    public long getDocumentCount(@NotNull String namespace, @NotNull String categoryId) {
        final Query query = new Query(
                Criteria.where(FIELD_DOCUMENT_NAMESPACE).is(namespace)
                        .and(FIELD_DOCUMENT_CATEGORY_ID).is(categoryId)
        );
        return mongoTemplate.count(query, COLLECTION_DOCUMENT);
    }

    public @NotNull Page<ObjectId> queryPagedDocumentId(
            @NotNull String namespace, @NotNull ObjectId categoryId,
            int page, int size
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        final Query query = new Query(
                Criteria.where(FIELD_DOCUMENT_NAMESPACE).is(namespace)
                        .and(FIELD_DOCUMENT_CATEGORY_ID).is(categoryId)
        );
        query.fields().include(FIELD_ID);
        final long count = mongoTemplate.count(query, COLLECTION_DOCUMENT);
        final List<ObjectId> result = mongoTemplate
                .find(query, Document.class, COLLECTION_DOCUMENT).stream()
                .map(document -> document.getObjectId(FIELD_ID))
                .toList();
        return new PageImpl<>(result, pageable, count);
    }

    public @NotNull DocumentSchemaVo findDocumentSchema(@NotNull String namespace) {
        return new DocumentSchemaVo(
                documentSchemaRepository.findByNamespace(namespace)
                        .orElseThrow(() -> new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST))
        );
    }

    public @NotNull List<DocumentAnnotationVo> exportAll(
            @NotNull String namespace, @NotNull String categoryId
    ) {
        final ObjectId uid = NulSecurityConfig.getContextAccount().getId();

        final Query query = new Query(
                Criteria.where(FIELD_DOCUMENT_NAMESPACE).is(namespace)
                        .and(FIELD_DOCUMENT_CATEGORY_ID).is(categoryId)
        );
        final List<Document> documentList = mongoTemplate.find(query, Document.class, COLLECTION_DOCUMENT);
        final List<ObjectId> documentIdList = documentList.stream()
                .map(document -> document.getObjectId(FIELD_ID)).toList();

        final Query queryAnnotations = new Query(
                Criteria.where(FIELD_ANNOTATIONS_UID).is(uid)
                        .and(FIELD_ANNOTATIONS_DOCUMENT_ID).in(documentIdList)
        );
        final List<Document> annotations = mongoTemplate.find(queryAnnotations, Document.class, COLLECTION_ANNOTATIONS);
        final Map<ObjectId, Document> annotationsMap = new HashMap<>();
        for (final Document annotation : annotations) {
            final ObjectId documentId = annotation.getObjectId(FIELD_ANNOTATIONS_DOCUMENT_ID);
            final Document annotationContent = (Document) annotation.get(FIELD_ANNOTATIONS);
            annotationsMap.put(documentId, annotationContent);
        }

        final List<DocumentAnnotationVo> result = new ArrayList<>(documentList.size());
        for (final Document document : documentList) {
            final ObjectId documentId = document.getObjectId(FIELD_ID);
            final Document documentContent = (Document) document.get(FIELD_DOCUMENT);
            final Document annotationContent = annotationsMap.get(documentId);
            result.add(new DocumentAnnotationVo(documentContent, annotationContent));
        }

        return result;
    }

    public void insertDocumentSchema() {

    }

    public void updateDocumentSchema() {

    }

    public void insertCategory() {

    }

    private @NotNull JsonSchema getSchemaValidator(@NotNull String namespace) {
        JsonSchema schema = schemaCache.getIfPresent(namespace);
        if (schema != null) {
            return schema;
        }
        final Query querySchema = new Query(Criteria.where(FIELD_SCHEMA_NAMESPACE).is(namespace));
        querySchema.fields().include(FIELD_SCHEMA_ANNOTATION_SCHEMA);
        final Document documentSchema = mongoTemplate.findOne(querySchema, Document.class, COLLECTION_SCHEMA);
        if (documentSchema == null) {
            throw new NulException(NulExceptionConstants.DOCUMENT_SCHEMA_NOT_EXIST);
        }
        final Document annotationSchema = (Document) documentSchema.get(FIELD_SCHEMA_ANNOTATION_SCHEMA);
        final JsonNode annotationNode;
        try {
            annotationNode = new ObjectMapper().readTree(annotationSchema.toJson());
        } catch (JsonProcessingException e) {
            throw new NulException(NulExceptionConstants.DOCUMENT_SCHEMA_INVALID);
        }
        final JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        schema = factory.getSchema(annotationNode);
        schemaCache.put(namespace, schema);
        return schema;
    }
}
