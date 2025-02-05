package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureImageProblem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultureImageProblemRepository
        extends MongoRepository<NulAgricultureImageProblem, String> {
    @NotNull Optional<NulAgricultureImageProblem> findByUidAndCategoryAndId(Long uid, Long category, Long id);

    @NotNull List<NulAgricultureImageProblem> findAllByUidAndCategory(Long uid, Long category);
}
