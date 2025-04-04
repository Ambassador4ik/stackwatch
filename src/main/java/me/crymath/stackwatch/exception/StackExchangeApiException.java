package me.crymath.stackwatch.exception;

import lombok.Getter;

/** Represents an error response from the StackWatch API. */
@Getter
public class StackExchangeApiException extends RuntimeException {

    private final int statusCode;

    public StackExchangeApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public StackExchangeApiException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
