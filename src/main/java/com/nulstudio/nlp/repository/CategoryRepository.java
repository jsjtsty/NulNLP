package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulCategory;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<NulCategory, ObjectId> {
    @NotNull List<NulCategory> findByNamespace(String namespace);
}
