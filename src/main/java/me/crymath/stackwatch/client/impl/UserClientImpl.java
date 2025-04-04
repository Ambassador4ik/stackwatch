package me.crymath.stackwatch.client.impl;

import java.util.List;
import me.crymath.stackwatch.client.AbstractStackExchangeClient;
import me.crymath.stackwatch.client.UserClient;
import me.crymath.stackwatch.model.StackExchangeResponse;
import me.crymath.stackwatch.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class UserClientImpl extends AbstractStackExchangeClient implements UserClient {

    public UserClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public User getUser(int userId) {
        return execute(() -> restTemplate
                .exchange(
                        "/users/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<User>>() {},
                        userId)
                .getBody()
                .getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }

    @Override
    public User getAuthenticatedUser() {
        return execute(() -> restTemplate
                .exchange("/me", HttpMethod.GET, null, new ParameterizedTypeReference<StackExchangeResponse<User>>() {})
                .getBody()
                .getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<User> listUsers() {
        return execute(() -> restTemplate
                .exchange(
                        "/users?order=desc&sort=reputation",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<User>>() {})
                .getBody()
                .getItems());
    }
}
