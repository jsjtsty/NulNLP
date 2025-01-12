package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<NulAccount, Long> {
    Optional<NulAccount> findByUsername(String username);
}
