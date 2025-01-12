package com.nulstudio.nlp.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulstudio.nlp.domain.dto.AgricultureImageDto;
import com.nulstudio.nlp.entity.NulAgricultureImage;
import com.nulstudio.nlp.repository.AgricultureImageRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgricultureImageService {

    @Resource
    private AgricultureImageRepository agricultureImageRepository;

    public long getCount() {
        return agricultureImageRepository.count();
    }

    @NotNull
    public NulAgricultureImage getEntryById(long id) {
        return agricultureImageRepository.findById(id)
                .orElseThrow();
    }

    public void updateEntry(long id, @NotNull AgricultureImageDto agricultureImage) {
        agricultureImageRepository.updateEntry(id, agricultureImage.getAvailable(), agricultureImage.getSensible());
    }

    @NotNull
    public String exportJson() throws JsonProcessingException {
        final List<NulAgricultureImage> list = agricultureImageRepository.findAll();
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }
}
