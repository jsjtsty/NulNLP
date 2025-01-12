package com.nulstudio.nlp.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulstudio.nlp.config.NulSecurityConfig;
import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageBenchmarkVo;
import com.nulstudio.nlp.service.service.AgricultureImageBenchmarkService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgricultureImageBenchmarkControllerService {

    @Resource
    private AgricultureImageBenchmarkService benchmarkService;

    public long getCount() {
        return benchmarkService.getCount();
    }

    @NotNull
    public AgricultureImageBenchmarkVo getEntryById(long id) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        return benchmarkService.getBenchmark(account.getId(), id);
    }

    public void updateEntryById(long id, AgricultureImageBenchmarkDto benchmarkDto) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        benchmarkService.updateBenchmark(account.getId(), id, benchmarkDto);
    }

    @NotNull
    public String exportJson() throws JsonProcessingException {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        final List<AgricultureImageBenchmarkVo> data = benchmarkService.findAll(account.getId());
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
}
