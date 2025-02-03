package com.nulstudio.nlp.util;

import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class NulDataUtil {
    @NotNull
    public static ResponseEntity<byte[]> outputCompressedJson(@NotNull String content, @NotNull String prefix) {
        final LocalDateTime now = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        final String formatedTime = now.format(formatter);

        final byte[] jsonBytes = content.getBytes(StandardCharsets.UTF_8);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (XZCompressorOutputStream xzOutputStream = new XZCompressorOutputStream(byteArrayOutputStream)) {
            xzOutputStream.write(jsonBytes);
        } catch (IOException e) {
            throw new NulException(NulExceptionConstants.INTERNAL_SERVER_ERROR);
        }

        final String fileName = prefix + "_" + formatedTime + ".json.xz";
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }

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
