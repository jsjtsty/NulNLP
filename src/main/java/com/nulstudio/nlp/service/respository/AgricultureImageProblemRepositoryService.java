package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageProblem;
import com.nulstudio.nlp.entity.NulAgricultureImageProblem;
import com.nulstudio.nlp.repository.AgricultureImageProblemRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgricultureImageProblemRepositoryService {

    @Resource
    private AgricultureImageProblemRepository repository;

    @Resource
    @Lazy
    private AgricultureImageProblemRepositoryService repositoryService;

    @NotNull
    public Optional<CachedAgricultureImageProblem> find(long uid, long category, long id) {
        return repository.findByUidAndCategoryAndId(uid, category, id)
                .map(CachedAgricultureImageProblem::new);
    }

    @Transactional
    public @NotNull CachedAgricultureImageProblem save(@NotNull CachedAgricultureImageProblem problem) {
        if (problem.getObjectId() == null) {
            final Optional<CachedAgricultureImageProblem> existing =
                    repositoryService.find(problem.getUid(), problem.getCategory(), problem.getId());
            existing.ifPresent(cachedAgricultureImageProblem ->
                    problem.setObjectId(cachedAgricultureImageProblem.getObjectId()));
        }
        final NulAgricultureImageProblem result = repository.save(problem.restore());
        return new CachedAgricultureImageProblem(result);
    }

    @NotNull
    public List<CachedAgricultureImageProblem> findAll(long uid, long category) {
        return repository.findAllByUidAndCategory(uid, category).stream()
                .map(CachedAgricultureImageProblem::new)
                .toList();
    }
}
