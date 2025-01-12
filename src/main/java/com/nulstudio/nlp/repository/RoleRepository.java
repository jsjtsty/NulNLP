package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<NulRole, Integer> {
}
