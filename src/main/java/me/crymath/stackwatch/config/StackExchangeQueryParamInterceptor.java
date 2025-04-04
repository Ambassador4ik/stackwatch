package me.crymath.stackwatch.config;

import java.io.IOException;
import java.net.URI;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

/** Interceptor to add StackWatch query parameters (site, key, access_token) to every request. */
public class StackExchangeQueryParamInterceptor implements ClientHttpRequestInterceptor {

    private final StackExchangeProperties properties;

    public StackExchangeQueryParamInterceptor(StackExchangeProperties properties) {
        this.properties = properties;
    }

    @NotNull
    @Override
    public ClientHttpResponse intercept(
            @NotNull HttpRequest request, byte @NotNull [] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpRequest modifiedRequest = buildModifiedRequest(request);
        return execution.execute(modifiedRequest, body);
    }

    private HttpRequest buildModifiedRequest(HttpRequest request) {
        URI originalUri = request.getURI();
        StringBuilder newQuery = new StringBuilder();

        if (originalUri.getQuery() != null && !originalUri.getQuery().isEmpty()) {
            newQuery.append(originalUri.getQuery()).append("&");
        }
        newQuery.append("site=").append(properties.getSite());
        if (properties.getApiKey() != null && !properties.getApiKey().isEmpty()) {
            newQuery.append("&key=").append(properties.getApiKey());
        }
        if (properties.getAccessToken() != null && !properties.getAccessToken().isEmpty()) {
            newQuery.append("&access_token=").append(properties.getAccessToken());
        }

        final URI newUri;
        try {
            newUri = new URI(
                    originalUri.getScheme(),
                    originalUri.getAuthority(),
                    originalUri.getPath(),
                    newQuery.toString(),
                    originalUri.getFragment());
        } catch (Exception e) {
            // Fallback to the original URI if any error occurs during URI construction
            return request;
        }

        return new HttpRequestWrapper(request) {
            @NotNull
            @Override
            public URI getURI() {
                return newUri;
            }
        };
    }
}
