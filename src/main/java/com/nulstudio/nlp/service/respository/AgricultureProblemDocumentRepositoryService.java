package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.entity.NulAgricultureProblemDocument;
import com.nulstudio.nlp.repository.AgricultureProblemDocumentRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgricultureProblemDocumentRepositoryService {

    @Resource
    private AgricultureProblemDocumentRepository repository;

    @NotNull
    public Optional<NulAgricultureProblemDocument> find(long category, long id) {
        return repository.findByCategoryAndId(category, id);
    }

    @NotNull
    public List<NulAgricultureProblemDocument> findAll(long category) {
        return repository.findAllByCategory(category);
    }

    public long getCount(long category) {
        return repository.countByCategory(category);
    }

    public boolean exists(long category, long id) {
        return repository.existsByCategoryAndId(category, id);
    }
}
