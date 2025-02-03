package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureImageBenchmarkDocument;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultureImageBenchmarkDocumentRepository
        extends MongoRepository<NulAgricultureImageBenchmarkDocument, String> {

    @NotNull Optional<NulAgricultureImageBenchmarkDocument> findByCategoryAndId(Long category, Long id);

    @NotNull List<NulAgricultureImageBenchmarkDocument> findAllByCategory(Long category);

    long countByCategory(Long category);

    boolean existsByCategoryAndId(Long category, Long id);
}
