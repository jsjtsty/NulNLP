package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.cache.CachedAgricultureProblem;
import com.nulstudio.nlp.domain.dto.AgricultureProblemDto;
import com.nulstudio.nlp.domain.vo.AgricultureProblemVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.entity.NulAgricultureProblemDocument;
import com.nulstudio.nlp.entity.NulCategory;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.service.respository.AgricultureProblemDocumentRepositoryService;
import com.nulstudio.nlp.service.respository.AgricultureProblemRepositoryService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgricultureProblemService {
    @Resource
    private AgricultureProblemRepositoryService repositoryService;

    @Resource
    private AgricultureProblemDocumentRepositoryService documentRepositoryService;

    @Resource
    private CategoryService categoryService;

    public long getCount(long category) {
        return documentRepositoryService.getCount(category);
    }

    @NotNull
    public List<CategoryVo> getCategories() {
        return categoryService.getCategoryList(NulCategory.NAMESPACE_PROBLEM);
    }

    @NotNull
    public AgricultureProblemVo find(long uid, long category, long id) {
        final NulAgricultureProblemDocument document = documentRepositoryService.find(category, id)
                .orElseThrow(() -> new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST));
        final Optional<CachedAgricultureProblem> problem = repositoryService.find(uid, category, id);
        return problem.map(entry -> new AgricultureProblemVo(document, entry))
                .orElse(new AgricultureProblemVo(document));
    }

    public void update(
            long uid, long category, long id, @NotNull AgricultureProblemDto problem
    ) {
        if (!documentRepositoryService.exists(category, id)) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }

        final CachedAgricultureProblem cached = new CachedAgricultureProblem();
        cached.setCategory(category);
        cached.setId(id);
        cached.setUid(uid);
        cached.setAnswer(problem.answer());
        cached.setRejected(problem.rejected());

        repositoryService.save(cached);
    }

    @NotNull
    public List<AgricultureProblemVo> findAll(long uid, long category) {
        final List<NulAgricultureProblemDocument> documents = documentRepositoryService.findAll(category);
        final List<CachedAgricultureProblem> problems = repositoryService.findAll(uid, category);
        final Map<Long, CachedAgricultureProblem> problemMap = new HashMap<>();
        for (final CachedAgricultureProblem problem : problems) {
            problemMap.put(problem.getId(), problem);
        }
        final List<AgricultureProblemVo> result = new ArrayList<>(documents.size());
        for (final NulAgricultureProblemDocument document : documents) {
            result.add(new AgricultureProblemVo(
                    document,
                    problemMap.getOrDefault(document.getId(), new CachedAgricultureProblem())
            ));
        }
        return result;
    }
}
