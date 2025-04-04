package me.crymath.stackwatch.exception;

import lombok.Getter;

/** Exception indicating that the StackWatch API rate limit has been exceeded. */
@Getter
public class StackExchangeRateLimitExceededException extends StackExchangeApiException {

    private final int backoffSeconds;

    public StackExchangeRateLimitExceededException(String message, int statusCode, int backoffSeconds) {
        super(message, statusCode);
        this.backoffSeconds = backoffSeconds;
    }
}
