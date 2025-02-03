package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureProblemDocument;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultureProblemDocumentRepository
        extends MongoRepository<NulAgricultureProblemDocument, String> {
    @NotNull Optional<NulAgricultureProblemDocument> findByCategoryAndId(Long category, Long id);

    @NotNull List<NulAgricultureProblemDocument> findAllByCategory(Long category);

    long countByCategory(Long category);

    boolean existsByCategoryAndId(Long category, Long id);
}
