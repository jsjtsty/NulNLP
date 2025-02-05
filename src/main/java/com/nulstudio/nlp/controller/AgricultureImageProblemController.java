package com.nulstudio.nlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AgricultureImageProblemDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageProblemVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.service.controller.AgricultureImageProblemControllerService;
import com.nulstudio.nlp.util.NulDataUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cleaner/image-problem")
public class AgricultureImageProblemController {
    @Resource
    private AgricultureImageProblemControllerService problemService;

    @GetMapping("/category")
    public NulResult<List<CategoryVo>> getCategories() {
        return NulResult.response(problemService.getCategories());
    }

    @GetMapping("/category/{category}")
    public NulResult<Long> getCount(@PathVariable long category) {
        return NulResult.response(problemService.getCount(category));
    }

    @GetMapping("/category/{category}/entry/{id}")
    public NulResult<AgricultureImageProblemVo> getEntry(
            @PathVariable long category, @PathVariable long id
    ) {
        return NulResult.response(problemService.find(category, id));
    }

    @PutMapping("/category/{category}/entry/{id}")
    public NulResult<Void> putEntry(
            @PathVariable long id, @PathVariable long category,
            @RequestBody AgricultureImageProblemDto problemDto
    ) {
        problemService.update(category, id, problemDto);
        return NulResult.response();
    }

    @GetMapping("/category/{category}/export")
    public ResponseEntity<byte[]> export(@PathVariable long category) throws JsonProcessingException {
        return NulDataUtil.outputJson(
                problemService.exportJson(category),
                String.format("image_problem_category_%d", category)
        );
    }
}
