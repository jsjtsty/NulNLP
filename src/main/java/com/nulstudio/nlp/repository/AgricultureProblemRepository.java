package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureProblem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultureProblemRepository extends MongoRepository<NulAgricultureProblem, Long> {
    @NotNull Optional<NulAgricultureProblem> findByUidAndCategoryAndId(Long uid, Long category, Long id);

    @NotNull List<NulAgricultureProblem> findAllByUidAndCategory(Long uid, Long category);
}
