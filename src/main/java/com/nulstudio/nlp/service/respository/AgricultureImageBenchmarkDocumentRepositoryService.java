package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.entity.NulAgricultureImageBenchmarkDocument;
import com.nulstudio.nlp.repository.AgricultureImageBenchmarkDocumentRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgricultureImageBenchmarkDocumentRepositoryService {

    @Resource
    private AgricultureImageBenchmarkDocumentRepository repository;

    @NotNull
    public Optional<NulAgricultureImageBenchmarkDocument> findById(long id) {
        return repository.findById(id);
    }

    @NotNull
    public List<NulAgricultureImageBenchmarkDocument> findAll() {
        return repository.findAll();
    }

    public long getCount() {
        return repository.count();
    }

    public boolean existsById(long id) {
        return repository.existsById(id);
    }
}
