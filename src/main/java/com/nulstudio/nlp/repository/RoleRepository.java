package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulRole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<NulRole, ObjectId> {
}
