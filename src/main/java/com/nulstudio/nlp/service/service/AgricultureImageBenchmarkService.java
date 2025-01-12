package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageBenchmark;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageBenchmarkVo;
import com.nulstudio.nlp.entity.NulAgricultureImageBenchmarkDocument;
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

    public long getCount() {
        return documentRepositoryService.getCount();
    }

    @NotNull
    public AgricultureImageBenchmarkVo getBenchmark(long uid, long id) {
        final NulAgricultureImageBenchmarkDocument document = documentRepositoryService.findById(id)
                .orElseThrow(() -> new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST));
        final Optional<CachedAgricultureImageBenchmark> benchmark = repositoryService.find(uid, id);
        return benchmark.map(entry -> new AgricultureImageBenchmarkVo(document, entry))
                .orElse(new AgricultureImageBenchmarkVo(document));
    }

    public void updateBenchmark(long uid, long id, @NotNull AgricultureImageBenchmarkDto benchmark) {
        if (!documentRepositoryService.existsById(id)) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }

        final CachedAgricultureImageBenchmark cached = new CachedAgricultureImageBenchmark();
        cached.setUid(uid);
        cached.setEntryId(id);
        cached.setAnswer(benchmark.answer());
        cached.setLogic(benchmark.logic());
        cached.setOptions(benchmark.options());
        cached.setQuestion(benchmark.question());
        cached.setOptionAnalysis(benchmark.optionAnalysis());

        repositoryService.save(cached);
    }

    @NotNull
    public List<AgricultureImageBenchmarkVo> findAll(long uid) {
        final List<NulAgricultureImageBenchmarkDocument> documents = documentRepositoryService.findAll();
        final List<CachedAgricultureImageBenchmark> benchmarks = repositoryService.findAll();
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
