package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageBenchmark;
import com.nulstudio.nlp.entity.NulAgricultureImageBenchmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgricultureImageBenchmarkRepository extends JpaRepository<NulAgricultureImageBenchmark, Long> {
    Optional<CachedAgricultureImageBenchmark> findByUidAndEntryId(Long uid, Long entryId);
}
