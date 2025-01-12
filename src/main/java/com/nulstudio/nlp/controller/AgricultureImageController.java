package com.nulstudio.nlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nulstudio.nlp.common.NulConstants;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AgricultureImageDto;
import com.nulstudio.nlp.entity.NulAgricultureImage;
import com.nulstudio.nlp.service.service.AgricultureImageService;
import com.nulstudio.nlp.util.NulDataUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cleaner/image")
public class AgricultureImageController {

    @Resource
    private AgricultureImageService imageService;

    @GetMapping
    public NulResult<Long> getCount() {
        return NulResult.response(imageService.getCount());
    }

    @GetMapping("/{id}")
    public NulResult<NulAgricultureImage> getEntry(@PathVariable long id) {
        return NulResult.response(imageService.getEntryById(id));
    }

    @PutMapping("/{id}")
    public NulResult<Void> putEntry(@PathVariable long id, @RequestBody AgricultureImageDto entry) {
        imageService.updateEntry(id, entry);
        return NulResult.response();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export() throws JsonProcessingException {
        return NulDataUtil.outputJson(imageService.exportJson(), NulConstants.JSON_AGRICULTURE_IMAGE_PREFIX);
    }
}
