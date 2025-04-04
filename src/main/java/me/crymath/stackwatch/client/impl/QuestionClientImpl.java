package me.crymath.stackwatch.client.impl;

import java.util.List;
import me.crymath.stackwatch.client.AbstractStackExchangeClient;
import me.crymath.stackwatch.client.QuestionClient;
import me.crymath.stackwatch.model.Question;
import me.crymath.stackwatch.model.StackExchangeResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class QuestionClientImpl extends AbstractStackExchangeClient implements QuestionClient {

    public QuestionClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Question> listQuestions() {
        return execute(() -> restTemplate
                .exchange(
                        "/questions?order=desc&sort=activity",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Question>>() {})
                .getBody()
                .getItems());
    }

    @Override
    public Question getQuestion(int questionId) {
        return execute(() -> restTemplate
                .exchange(
                        "/questions/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Question>>() {},
                        questionId)
                .getBody()
                .getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<Question> listQuestionsByTag(String tag) {
        return execute(() -> restTemplate
                .exchange(
                        "/questions?order=desc&sort=activity&tagged={tag}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Question>>() {},
                        tag)
                .getBody()
                .getItems());
    }
}
