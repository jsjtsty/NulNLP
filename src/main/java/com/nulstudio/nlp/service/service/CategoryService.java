package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.service.respository.CategoryRepositoryService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryRepositoryService repositoryService;

    @NotNull
    public List<CategoryVo> getCategoryList(@NotNull String namespace) {
        return repositoryService.getCategoryList(namespace).stream()
                .map(CategoryVo::new)
                .toList();
    }
}
