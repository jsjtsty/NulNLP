package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulDocumentSchema;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentSchemaRepository extends MongoRepository<NulDocumentSchema, ObjectId> {
    @NotNull Optional<NulDocumentSchema> findByNamespace(String namespace);
}
