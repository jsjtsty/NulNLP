package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageBenchmark;
import com.nulstudio.nlp.entity.NulAgricultureImageBenchmark;
import com.nulstudio.nlp.repository.AgricultureImageBenchmarkRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "agriculture_image_benchmark", key = "#uid + ':' + #id", unless = "#result == null")
    public Optional<CachedAgricultureImageBenchmark> find(long uid, long id) {
        return repository.findByUidAndEntryId(uid, id);
    }

    @CachePut(value = "agriculture_image_benchmark", key = "#benchmark.uid + ':' + #benchmark.entryId")
    @Transactional
    public CachedAgricultureImageBenchmark save(@NotNull CachedAgricultureImageBenchmark benchmark) {
        if (benchmark.getId() == null) {
            final Optional<CachedAgricultureImageBenchmark> existing =
                    repositoryService.find(benchmark.getUid(), benchmark.getEntryId());
            existing.ifPresent(cachedAgricultureImageBenchmark ->
                    benchmark.setId(cachedAgricultureImageBenchmark.getId()));
        }
        final NulAgricultureImageBenchmark result = repository.save(benchmark.restore());
        return new CachedAgricultureImageBenchmark(result);
    }

    @NotNull
    public List<CachedAgricultureImageBenchmark> findAllByUid(long uid) {
        final List<CachedAgricultureImageBenchmark> result = new ArrayList<>();
        final List<NulAgricultureImageBenchmark> queryResult = repository.findAllByUid(uid);
        for (final NulAgricultureImageBenchmark benchmark : queryResult) {
            result.add(new CachedAgricultureImageBenchmark(benchmark));
        }
        return result;
    }
}
