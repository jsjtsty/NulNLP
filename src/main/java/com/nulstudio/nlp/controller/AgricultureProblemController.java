package com.nulstudio.nlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.dto.AgricultureProblemDto;
import com.nulstudio.nlp.domain.vo.AgricultureProblemVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.service.controller.AgricultureProblemControllerService;
import com.nulstudio.nlp.util.NulDataUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cleaner/problem")
public class AgricultureProblemController {

    @Resource
    private AgricultureProblemControllerService problemService;

    @GetMapping("/category")
    public NulResult<List<CategoryVo>> getCategories() {
        return NulResult.response(problemService.getCategories());
    }

    @GetMapping("/category/{category}")
    public NulResult<Long> getCount(@PathVariable long category) {
        return NulResult.response(problemService.getCount(category));
    }

    @GetMapping("/category/{category}/entry/{id}")
    public NulResult<AgricultureProblemVo> getEntry(
            @PathVariable long category, @PathVariable long id
    ) {
        return NulResult.response(problemService.find(category, id));
    }

    @PutMapping("/category/{category}/entry/{id}")
    public NulResult<Void> putEntry(
            @PathVariable long id, @PathVariable long category,
            @RequestBody AgricultureProblemDto problemDto
    ) {
        problemService.update(category, id, problemDto);
        return NulResult.response();
    }

    @GetMapping("/category/{category}/export")
    public ResponseEntity<byte[]> export(@PathVariable long category) throws JsonProcessingException {
        return NulDataUtil.outputJson(
                problemService.exportJson(category),
                String.format("problem_category_%d", category)
        );
    }
}
