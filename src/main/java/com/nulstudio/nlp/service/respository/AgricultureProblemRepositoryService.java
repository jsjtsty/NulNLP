package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedAgricultureProblem;
import com.nulstudio.nlp.entity.NulAgricultureProblem;
import com.nulstudio.nlp.repository.AgricultureProblemRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgricultureProblemRepositoryService {

    @Resource
    private AgricultureProblemRepository repository;

    @Resource
    @Lazy
    private AgricultureProblemRepositoryService repositoryService;

    @NotNull
    public Optional<CachedAgricultureProblem> find(long uid, long category, long id) {
        return repository.findByUidAndCategoryAndId(uid, category, id)
                .map(CachedAgricultureProblem::new);
    }

    @Transactional
    public @NotNull CachedAgricultureProblem save(@NotNull CachedAgricultureProblem problem) {
        if (problem.getObjectId() == null) {
            final Optional<CachedAgricultureProblem> existing =
                    repositoryService.find(problem.getUid(), problem.getCategory(), problem.getId());
            existing.ifPresent(cachedAgricultureProblem ->
                    problem.setObjectId(cachedAgricultureProblem.getObjectId()));
        }
        final NulAgricultureProblem result = repository.save(problem.restore());
        return new CachedAgricultureProblem(result);
    }

    @NotNull
    public List<CachedAgricultureProblem> findAll(long uid, long category) {
        return repository.findAllByUidAndCategory(uid, category).stream()
                .map(CachedAgricultureProblem::new)
                .toList();
    }

}
