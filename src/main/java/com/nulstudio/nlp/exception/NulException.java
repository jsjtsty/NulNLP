package com.nulstudio.nlp.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

/**
 * Exception for all fail responses in this application.
 * @author nullsty
 * @since 2.0
 */
public class NulException extends RuntimeException {

    /**
     * Error code.
     */
    protected final int code;

    /**
     * HTTP status.
     */
    protected final HttpStatus httpStatus;

    /**
     * Create a new exception with code 0 and empty error message.
     */
    public NulException() {
        this.code = 0;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Create a new exception with the specified code and empty error message.
     * @param code error code
     */
    public NulException(int code) {
        this.code = code;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Create a new exception with the specified code and error message.
     * @param code error code
     * @param message error message, must be non-null
     */
    public NulException(int code, @NotNull String message) {
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Initialize an exception by exception constant.
     * @param exceptionConstants exception constant, must be non-null
     */
    public NulException(@NotNull NulExceptionConstants exceptionConstants) {
        super(exceptionConstants.getMessage());
        this.code = exceptionConstants.getCode();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    /**
     * Initialize an exception by exception constant and message.
     * @param exceptionConstants exception constant, must be non-null
     * @param message error message, must be non-null
     */
    public NulException(@NotNull NulExceptionConstants exceptionConstants, @NotNull String message) {
        super(message);
        this.code = exceptionConstants.getCode();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    /**
     * Get code of the exception.
     * @return code of the exception
     */
    public int getCode() {
        return code;
    }

    /**
     * Get HTTP status.
     * @return indicated HTTP status
     */
    public @NotNull HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
