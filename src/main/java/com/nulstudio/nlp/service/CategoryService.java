package com.nulstudio.nlp.service;

import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.repository.CategoryRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    public @NotNull List<CategoryVo> getCategoryList(@NotNull String namespace) {
        return categoryRepository.findByNamespace(namespace).stream()
                .map(CategoryVo::new)
                .toList();
    }
}
