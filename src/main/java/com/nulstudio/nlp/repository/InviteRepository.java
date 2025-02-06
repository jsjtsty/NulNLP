package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulInvite;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteRepository extends MongoRepository<NulInvite, ObjectId> {
    Optional<NulInvite> findByInviteCode(String inviteCode);

    boolean existsByInviteCode(String inviteCode);
}
