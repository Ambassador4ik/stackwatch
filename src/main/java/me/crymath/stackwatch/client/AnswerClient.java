package me.crymath.stackwatch.client;

import java.util.List;
import me.crymath.stackwatch.model.Answer;

public interface AnswerClient {
    List<Answer> listAnswers();

    Answer getAnswer(int answerId);

    List<Answer> listAnswersForQuestion(int questionId);
}
