package me.crymath.stackwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @JsonProperty("question_id")
    private int questionId;

    private String title;
    private String body;

    @JsonProperty("creation_date")
    private long creationDate;

    @JsonProperty("last_activity_date")
    private long lastActivityDate;

    private List<String> tags;
    private User owner;
    private int score;
}
