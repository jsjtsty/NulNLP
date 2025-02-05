package com.nulstudio.nlp.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulstudio.nlp.config.NulSecurityConfig;
import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.domain.dto.AgricultureImageProblemDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageProblemVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.service.service.AgricultureImageProblemService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgricultureImageProblemControllerService {
    @Resource
    private AgricultureImageProblemService problemService;

    public long getCount(long category) {
        return problemService.getCount(category);
    }

    public List<CategoryVo> getCategories() {
        return problemService.getCategories();
    }

    @NotNull
    public AgricultureImageProblemVo find(long category, long id) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        return problemService.find(account.getId(), category, id);
    }

    public void update(long category, long id, @NotNull AgricultureImageProblemDto problemDto) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        problemService.update(account.getId(), category, id, problemDto);
    }

    @NotNull
    public String exportJson(long category) throws JsonProcessingException {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        final List<AgricultureImageProblemVo> data = problemService.findAll(account.getId(), category);
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
}
