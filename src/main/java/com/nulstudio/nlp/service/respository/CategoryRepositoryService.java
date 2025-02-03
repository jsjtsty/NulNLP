package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedCategory;
import com.nulstudio.nlp.entity.NulCategory;
import com.nulstudio.nlp.repository.CategoryRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryRepositoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @NotNull
    public List<CachedCategory> getCategoryList(@NotNull String namespace) {
        final List<NulCategory> categoryList = categoryRepository.findAllByNamespace(namespace);
        final List<CachedCategory> result = new ArrayList<>();
        for (final NulCategory category : categoryList) {
            result.add(new CachedCategory(category));
        }
        return result;
    }
}
