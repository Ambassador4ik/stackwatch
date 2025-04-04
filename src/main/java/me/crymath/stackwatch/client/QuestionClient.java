package me.crymath.stackwatch.client;

import java.util.List;
import me.crymath.stackwatch.model.Question;

public interface QuestionClient {
    List<Question> listQuestions();

    Question getQuestion(int questionId);

    List<Question> listQuestionsByTag(String tag);
}
