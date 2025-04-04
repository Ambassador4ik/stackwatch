package me.crymath.stackwatch.client;

import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import me.crymath.stackwatch.exception.StackExchangeApiException;
import me.crymath.stackwatch.exception.StackExchangeRateLimitExceededException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/** Abstract base class for StackOverflow API clients. Centralizes error handling. */
@Slf4j
public abstract class AbstractStackExchangeClient {

    protected final RestTemplate restTemplate;

    protected AbstractStackExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /** Executes the given supplier and converts HTTP errors into StackWatchApiException. */
    protected <T> T execute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException e) {
            log.error("StackWatch API error: {}", e.getResponseBodyAsString());
            throw convertException(e);
        }
    }

    /** Converts an HttpClientErrorException into a structured exception. */
    protected RuntimeException convertException(HttpClientErrorException e) {
        String responseBody = e.getResponseBodyAsString();
        if (responseBody != null && responseBody.contains("throttle_violation")) {
            // A simple backoff value; a robust implementation would parse the actual value.
            int backoffSeconds = 10;
            return new StackExchangeRateLimitExceededException(
                    "Rate limit exceeded: " + responseBody, e.getStatusCode().value(), backoffSeconds);
        }
        return new StackExchangeApiException(
                "StackWatch API error: " + responseBody, e.getStatusCode().value(), e);
    }
}
