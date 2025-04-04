package me.crymath.stackwatch.service;

import lombok.Getter;
import me.crymath.stackwatch.client.AnswerClient;
import me.crymath.stackwatch.client.CommentClient;
import me.crymath.stackwatch.client.QuestionClient;
import me.crymath.stackwatch.client.TagClient;
import me.crymath.stackwatch.client.UserClient;
import me.crymath.stackwatch.client.impl.AnswerClientImpl;
import me.crymath.stackwatch.client.impl.CommentClientImpl;
import me.crymath.stackwatch.client.impl.QuestionClientImpl;
import me.crymath.stackwatch.client.impl.TagClientImpl;
import me.crymath.stackwatch.client.impl.UserClientImpl;
import org.springframework.web.client.RestTemplate;

/** Aggregates various StackExchange API clients for convenient use. */
@Getter
public class StackExchangeService {

    private final QuestionClient questionClient;
    private final AnswerClient answerClient;
    private final CommentClient commentClient;
    private final UserClient userClient;
    private final TagClient tagClient;

    public StackExchangeService(RestTemplate restTemplate) {
        this.questionClient = new QuestionClientImpl(restTemplate);
        this.answerClient = new AnswerClientImpl(restTemplate);
        this.commentClient = new CommentClientImpl(restTemplate);
        this.userClient = new UserClientImpl(restTemplate);
        this.tagClient = new TagClientImpl(restTemplate);
    }
}
