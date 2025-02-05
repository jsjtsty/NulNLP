package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.cache.CachedAgricultureImageProblem;
import com.nulstudio.nlp.domain.dto.AgricultureImageProblemDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageProblemVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.entity.NulAgricultureImageProblemDocument;
import com.nulstudio.nlp.entity.NulCategory;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.service.respository.AgricultureImageProblemDocumentRepositoryService;
import com.nulstudio.nlp.service.respository.AgricultureImageProblemRepositoryService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgricultureImageProblemService {
    @Resource
    private AgricultureImageProblemDocumentRepositoryService documentRepositoryService;

    @Resource
    private AgricultureImageProblemRepositoryService repositoryService;

    @Resource
    private CategoryService categoryService;

    public long getCount(long category) {
        return documentRepositoryService.getCount(category);
    }

    public @NotNull List<CategoryVo> getCategories() {
        return categoryService.getCategoryList(NulCategory.NAMESPACE_IMAGE_PROBLEM);
    }

    public @NotNull AgricultureImageProblemVo find(long uid, long category, long id) {
        final NulAgricultureImageProblemDocument document = documentRepositoryService.find(category, id)
                .orElseThrow(() -> new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST));
        final Optional<CachedAgricultureImageProblem> problem = repositoryService.find(uid, category, id);
        return problem.map(entry -> new AgricultureImageProblemVo(document, entry))
                .orElse(new AgricultureImageProblemVo(document));
    }

    public void update(
            long uid, long category, long id, @NotNull AgricultureImageProblemDto problem
    ) {
        if (!documentRepositoryService.exists(category, id)) {
            throw new NulException(NulExceptionConstants.DOCUMENT_NOT_EXIST);
        }

        final CachedAgricultureImageProblem cached = new CachedAgricultureImageProblem();
        cached.setCategory(category);
        cached.setId(id);
        cached.setUid(uid);
        cached.setAnswer(problem.answer());
        cached.setRejected(problem.rejected());

        repositoryService.save(cached);
    }

    public @NotNull List<AgricultureImageProblemVo> findAll(long uid, long category) {
        final List<NulAgricultureImageProblemDocument> documents = documentRepositoryService.findAll(category);
        final List<CachedAgricultureImageProblem> problems = repositoryService.findAll(uid, category);
        final Map<Long, CachedAgricultureImageProblem> problemMap = new HashMap<>();
        for (final CachedAgricultureImageProblem problem : problems) {
            problemMap.put(problem.getId(), problem);
        }
        final List<AgricultureImageProblemVo> result = new ArrayList<>(documents.size());
        for (final NulAgricultureImageProblemDocument document : documents) {
            result.add(new AgricultureImageProblemVo(
                    document,
                    problemMap.getOrDefault(document.getId(), new CachedAgricultureImageProblem())
            ));
        }
        return result;
    }
}
