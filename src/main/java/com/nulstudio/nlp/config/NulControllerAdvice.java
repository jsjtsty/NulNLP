package com.nulstudio.nlp.config;

import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global configurations for controllers.
 * Exception are handled in this class, except authority related errors.
 * @author nullsty
 * @since 2.0
 */
@RestControllerAdvice
public final class NulControllerAdvice {

    /**
     * Logger for this class.
     */
    private final Logger log = LoggerFactory.getLogger(NulControllerAdvice.class);

    /**
     * Exception handler for {@code NulException}, which is the common exception in this application.
     * @param exception exception to handle
     * @return a {@code NulResult} object, handling error information
     */
    @ExceptionHandler(NulException.class)
    public @NotNull ResponseEntity<NulResult<Void>> nulExceptionHandler(@NotNull NulException exception) {
        log.error("Error", exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(NulResult.fail(exception));
    }

    /**
     * Exception handler for {@code Exception}, handling system error.
     * @param exception exception to handle
     * @return a {@code NulResult} object, handling error information
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @NotNull NulResult<Void> internalExceptionHandler(@NotNull Exception exception) {
        log.error("Internal Error", exception);
        return NulResult.fail(NulExceptionConstants.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
