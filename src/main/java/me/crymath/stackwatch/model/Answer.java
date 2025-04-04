package me.crymath.stackwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @JsonProperty("answer_id")
    private int answerId;

    @JsonProperty("question_id")
    private int questionId;

    private String body;

    @JsonProperty("creation_date")
    private long creationDate;

    private int score;
    private User owner;
}
