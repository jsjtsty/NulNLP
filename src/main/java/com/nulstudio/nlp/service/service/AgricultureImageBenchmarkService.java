package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageBenchmark;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageBenchmarkVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.entity.NulAgricultureImageBenchmarkDocument;
import com.nulstudio.nlp.entity.NulCategory;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.service.respository.AgricultureImageBenchmarkDocumentRepositoryService;
import com.nulstudio.nlp.service.respository.AgricultureImageBenchmarkRepositoryService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgricultureImageBenchmarkService {
    @Resource
    private AgricultureImageBenchmarkDocumentRepositoryService documentRepositoryService;

    @Resource
    private AgricultureImageBenchmarkRepositoryService repositoryService;

    @Resource
    private CategoryService categoryService;

    public long getCount(long category) {
        return documentRepositoryService.getCount(category);
    }

    @NotNull
    public List<CategoryVo> getCategories() {
        return categoryService.getCategoryList(NulCategory.NAMESPACE_IMAGE_BENCHMARK);
    }

    @NotNull
    public AgricultureImageBenchmarkVo find(long uid, long category, long id) {
        final NulAgricultureImageBenchmarkDocument document = documentRepositoryService.find(category, id)
                .orElseThrow(() -> new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST));
        final Optional<CachedAgricultureImageBenchmark> benchmark = repositoryService.find(uid, category, id);
        return benchmark.map(entry -> new AgricultureImageBenchmarkVo(document, entry))
                .orElse(new AgricultureImageBenchmarkVo(document));
    }

    public void update(
            long uid, long category, long id, @NotNull AgricultureImageBenchmarkDto benchmark
    ) {
        if (!documentRepositoryService.exists(category, id)) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }

        final CachedAgricultureImageBenchmark cached = new CachedAgricultureImageBenchmark();
        cached.setUid(uid);
        cached.setCategory(category);
        cached.setEntryId(id);
        cached.setAnswer(benchmark.answer());
        cached.setLogic(benchmark.logic());
        cached.setOptions(benchmark.options());
        cached.setQuestion(benchmark.question());
        cached.setOptionAnalysis(benchmark.optionAnalysis());

        repositoryService.save(cached);
    }

    @NotNull
    public List<AgricultureImageBenchmarkVo> findAll(long uid, long category) {
        final List<NulAgricultureImageBenchmarkDocument> documents = documentRepositoryService.findAll(category);
        final List<CachedAgricultureImageBenchmark> benchmarks = repositoryService.findAll(uid, category);
        final Map<Long, CachedAgricultureImageBenchmark> benchmarkMap = new HashMap<>(benchmarks.size());
        for (final CachedAgricultureImageBenchmark benchmark : benchmarks) {
            benchmarkMap.put(benchmark.getEntryId(), benchmark);
        }
        final List<AgricultureImageBenchmarkVo> result = new ArrayList<>(documents.size());
        for (final NulAgricultureImageBenchmarkDocument document : documents) {
            result.add(new AgricultureImageBenchmarkVo(
                    document,
                    benchmarkMap.getOrDefault(document.getId(), new CachedAgricultureImageBenchmark()))
            );
        }
        return result;
    }
}
