package com.nulstudio.nlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AgricultureImageBenchmarkDto;
import com.nulstudio.nlp.domain.vo.AgricultureImageBenchmarkVo;
import com.nulstudio.nlp.service.controller.AgricultureImageBenchmarkControllerService;
import com.nulstudio.nlp.util.NulDataUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cleaner/image-benchmark")
public class AgricultureImageBenchmarkController {

    @Resource
    private AgricultureImageBenchmarkControllerService benchmarkService;

    @GetMapping
    public NulResult<Long> getCount() {
        return NulResult.response(benchmarkService.getCount());
    }

    @GetMapping("/{id}")
    public NulResult<AgricultureImageBenchmarkVo> getEntry(@PathVariable long id) {
        return NulResult.response(benchmarkService.getEntryById(id));
    }

    @PutMapping("/{id}")
    public NulResult<Void> putEntry(@PathVariable long id, @RequestBody AgricultureImageBenchmarkDto benchmarkDto) {
        benchmarkService.updateEntryById(id, benchmarkDto);
        return NulResult.response();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export() throws JsonProcessingException {
        return NulDataUtil.outputJson(benchmarkService.exportJson(), "image_benchmark");
    }

}
