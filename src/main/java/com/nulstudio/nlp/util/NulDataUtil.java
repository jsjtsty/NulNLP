package com.nulstudio.nlp.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class NulDataUtil {
    @NotNull
    public static ResponseEntity<byte[]> outputJson(@NotNull String content, @NotNull String prefix) {
        final LocalDateTime now = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        final String formatedTime = now.format(formatter);

        final String fileName = prefix + "_" + formatedTime + ".json";
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(content.getBytes(), headers, HttpStatus.OK);
    }
}
