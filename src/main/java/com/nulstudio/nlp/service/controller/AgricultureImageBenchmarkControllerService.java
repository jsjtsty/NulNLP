package com.nulstudio.nlp.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulstudio.nlp.config.NulSecurityConfig;
import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageBenchmarkVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.service.service.AgricultureImageBenchmarkService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgricultureImageBenchmarkControllerService {

    @Resource
    private AgricultureImageBenchmarkService benchmarkService;

    public long getCount(long category) {
        return benchmarkService.getCount(category);
    }

    public List<CategoryVo> getCategories() {
        return benchmarkService.getCategories();
    }

    @NotNull
    public AgricultureImageBenchmarkVo find(long category, long id) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        return benchmarkService.find(account.getId(), category, id);
    }

    public void update(long id, long category, @NotNull AgricultureImageBenchmarkDto benchmarkDto) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        benchmarkService.update(account.getId(), category, id, benchmarkDto);
    }

    @NotNull
    public String exportJson(long category) throws JsonProcessingException {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        final List<AgricultureImageBenchmarkVo> data = benchmarkService.findAll(account.getId(), category);
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
}
