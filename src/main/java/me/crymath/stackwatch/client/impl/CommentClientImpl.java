package me.crymath.stackwatch.client.impl;

import java.util.List;
import me.crymath.stackwatch.client.AbstractStackExchangeClient;
import me.crymath.stackwatch.client.CommentClient;
import me.crymath.stackwatch.model.Comment;
import me.crymath.stackwatch.model.StackExchangeResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class CommentClientImpl extends AbstractStackExchangeClient implements CommentClient {

    public CommentClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Comment> listComments() {
        return execute(() -> restTemplate
                .exchange(
                        "/comments?order=desc&sort=creation",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Comment>>() {})
                .getBody()
                .getItems());
    }

    @Override
    public Comment getComment(int commentId) {
        return execute(() -> restTemplate
                .exchange(
                        "/comments/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Comment>>() {},
                        commentId)
                .getBody()
                .getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<Comment> listCommentsForPost(int postId) {
        return execute(() -> restTemplate
                .exchange(
                        "/posts/{id}/comments?order=desc&sort=creation",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Comment>>() {},
                        postId)
                .getBody()
                .getItems());
    }
}
