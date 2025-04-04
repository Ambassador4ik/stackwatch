package me.crymath.stackwatch.client.impl;

import java.util.List;
import me.crymath.stackwatch.client.AbstractStackExchangeClient;
import me.crymath.stackwatch.client.AnswerClient;
import me.crymath.stackwatch.model.Answer;
import me.crymath.stackwatch.model.StackExchangeResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class AnswerClientImpl extends AbstractStackExchangeClient implements AnswerClient {

    public AnswerClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Answer> listAnswers() {
        return execute(() -> restTemplate
                .exchange(
                        "/answers?order=desc&sort=activity",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Answer>>() {})
                .getBody()
                .getItems());
    }

    @Override
    public Answer getAnswer(int answerId) {
        return execute(() -> restTemplate
                .exchange(
                        "/answers/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Answer>>() {},
                        answerId)
                .getBody()
                .getItems()
                .stream()
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<Answer> listAnswersForQuestion(int questionId) {
        return execute(() -> restTemplate
                .exchange(
                        "/questions/{id}/answers?order=desc&sort=activity",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StackExchangeResponse<Answer>>() {},
                        questionId)
                .getBody()
                .getItems());
    }
}
