package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<NulAccount, ObjectId> {
    Optional<NulAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}
