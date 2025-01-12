package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgricultureImageRepository extends JpaRepository<NulAgricultureImage, Long> {
    @Modifying
    @Transactional
    @Query("update NulAgricultureImage i set i.available = :available, i.sensible = :sensible where i.id = :id")
    int updateEntry(@Param("id") long id, @Param("available") Boolean available, @Param("sensible") Boolean sensible);
}
