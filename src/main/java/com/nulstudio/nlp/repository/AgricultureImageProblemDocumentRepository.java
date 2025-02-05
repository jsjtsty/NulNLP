package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureImageProblemDocument;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultureImageProblemDocumentRepository
        extends MongoRepository<NulAgricultureImageProblemDocument, String> {
    @NotNull Optional<NulAgricultureImageProblemDocument> findByCategoryAndId(long category, long id);

    @NotNull List<NulAgricultureImageProblemDocument> findAllByCategory(Long category);

    long countByCategory(Long category);

    boolean existsByCategoryAndId(Long category, Long id);
}
