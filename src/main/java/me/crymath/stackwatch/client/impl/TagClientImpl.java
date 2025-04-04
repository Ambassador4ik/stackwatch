package me.crymath.stackwatch.client.impl;

import java.util.List;
import me.crymath.stackwatch.client.AbstractStackExchangeClient;
import me.crymath.stackwatch.client.TagClient;
import me.crymath.stackwatch.model.StackExchangeResponse;
import me.crymath.stackwatch.model.Tag;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class TagClientImpl extends AbstractStackExchangeClient implements TagClient {

    public TagClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Tag> listTags() {
        return execute(() -> restTemplate
                .exchange(
                        "/tags?order=desc&sort=popular",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Tag>>() {})
                .getBody()
                .getItems());
    }

    @Override
    public Tag getTag(String tagName) {
        return execute(() -> restTemplate
                .exchange(
                        "/tags/{tag}/info",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Tag>>() {},
                        tagName)
                .getBody()
                .getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }
}
