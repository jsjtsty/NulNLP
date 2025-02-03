package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageBenchmark;
import com.nulstudio.nlp.entity.NulAgricultureImageBenchmark;
import com.nulstudio.nlp.repository.AgricultureImageBenchmarkRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgricultureImageBenchmarkRepositoryService {

    @Resource
    private AgricultureImageBenchmarkRepository repository;

    @Resource
    @Lazy
    private AgricultureImageBenchmarkRepositoryService repositoryService;

    //@Cacheable(value = "agriculture_image_benchmark", key = "#uid + ':' + #id", unless = "#result.isEmpty()")
    public Optional<CachedAgricultureImageBenchmark> find(long uid, long category, long id) {
        return repository.findByUidAndCategoryAndEntryId(uid, category, id)
                .map(CachedAgricultureImageBenchmark::new);
    }

    //@CachePut(value = "agriculture_image_benchmark", key = "#benchmark.uid + ':' + #benchmark.entryId")
    @Transactional
    public CachedAgricultureImageBenchmark save(@NotNull CachedAgricultureImageBenchmark benchmark) {
        if (benchmark.getId() == null) {
            final Optional<CachedAgricultureImageBenchmark> existing =
                    repositoryService.find(benchmark.getUid(), benchmark.getCategory(), benchmark.getEntryId());
            existing.ifPresent(cachedAgricultureImageBenchmark ->
                    benchmark.setId(cachedAgricultureImageBenchmark.getId()));
        }
        final NulAgricultureImageBenchmark result = repository.save(benchmark.restore());
        return new CachedAgricultureImageBenchmark(result);
    }

    @NotNull
    public List<CachedAgricultureImageBenchmark> findAll(long uid, long category) {
        return repository.findAllByUidAndCategory(uid, category).stream()
                .map(CachedAgricultureImageBenchmark::new)
                .toList();
    }
}
