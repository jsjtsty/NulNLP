package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.domain.cache.CachedCategory;
import com.nulstudio.nlp.entity.NulCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<NulCategory, Long> {
    @NotNull List<NulCategory> findAllByNamespace(String namespace);
}
