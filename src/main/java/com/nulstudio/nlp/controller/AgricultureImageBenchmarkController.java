package com.nulstudio.nlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageBenchmarkVo;
import com.nulstudio.nlp.domain.vo.CategoryVo;
import com.nulstudio.nlp.service.controller.AgricultureImageBenchmarkControllerService;
import com.nulstudio.nlp.util.NulDataUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cleaner/image-benchmark")
public class AgricultureImageBenchmarkController {

    @Resource
    private AgricultureImageBenchmarkControllerService benchmarkService;

    @GetMapping("/category")
    public NulResult<List<CategoryVo>> getCategories() {
        return NulResult.response(benchmarkService.getCategories());
    }

    @GetMapping("/category/{category}")
    public NulResult<Long> getCount(@PathVariable long category) {
        return NulResult.response(benchmarkService.getCount(category));
    }

    @GetMapping("/category/{category}/entry/{id}")
    public NulResult<AgricultureImageBenchmarkVo> getEntry(
            @PathVariable long category, @PathVariable long id
    ) {
        return NulResult.response(benchmarkService.find(category, id));
    }

    @PutMapping("/category/{category}/entry/{id}")
    public NulResult<Void> putEntry(
            @PathVariable long id, @PathVariable long category,
            @RequestBody AgricultureImageBenchmarkDto benchmarkDto
    ) {
        benchmarkService.update(id, category, benchmarkDto);
        return NulResult.response();
    }

    @GetMapping("/category/{category}/export")
    public ResponseEntity<byte[]> export(@PathVariable long category) throws JsonProcessingException {
        return NulDataUtil.outputCompressedJson(
                benchmarkService.exportJson(category),
                String.format("image_benchmark_category_%d", category)
        );
    }

}
