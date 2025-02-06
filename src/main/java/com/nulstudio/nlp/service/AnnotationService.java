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
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import jakarta.annotation.Resource;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class AnnotationService {
    @Resource
    private MongoTemplate mongoTemplate;

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

    public @Nullable Document findAnnotation(@NotNull ObjectId uid, @NotNull ObjectId objectId) {
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

    public void update(@NotNull ObjectId uid, @NotNull ObjectId objectId, @NotNull JsonNode annotations) {
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
        final Document documentAnnotations = new Document();
        documentAnnotations.put(FIELD_ANNOTATIONS, annotations);
        documentAnnotations.put(FIELD_ANNOTATIONS_DOCUMENT_ID, objectId);
        documentAnnotations.put(FIELD_ANNOTATIONS_UID, uid);

        final Query queryLegacy = new Query(
                Criteria.where(FIELD_ANNOTATIONS_DOCUMENT_ID).is(objectId)
                        .and(FIELD_ANNOTATIONS_UID).is(uid)
        );
        queryLegacy.fields().include(FIELD_ID);
        final Document documentLegacy = mongoTemplate.findOne(queryLegacy, Document.class, COLLECTION_ANNOTATIONS);
        if (documentLegacy != null) {
            documentAnnotations.put(FIELD_ID, documentLegacy.getString(FIELD_ID));
        }

        mongoTemplate.save(documentAnnotations, COLLECTION_ANNOTATIONS);
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
