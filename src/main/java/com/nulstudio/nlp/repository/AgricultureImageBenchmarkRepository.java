package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureImageBenchmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultureImageBenchmarkRepository extends JpaRepository<NulAgricultureImageBenchmark, Long> {

    List<NulAgricultureImageBenchmark> findAllByUidAndCategory(Long uid, Long category);

    Optional<NulAgricultureImageBenchmark> findByUidAndCategoryAndEntryId(long uid, long category, long id);
}
