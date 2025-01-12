package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<NulInvite, Long> {
    Optional<NulInvite> findByInviteCode(String inviteCode);
}
