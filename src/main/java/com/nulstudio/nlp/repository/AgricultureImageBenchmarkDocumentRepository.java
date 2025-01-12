package com.nulstudio.nlp.repository;

import com.nulstudio.nlp.entity.NulAgricultureImageBenchmarkDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgricultureImageBenchmarkDocumentRepository
        extends MongoRepository<NulAgricultureImageBenchmarkDocument, Long> {

}
