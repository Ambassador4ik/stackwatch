package me.crymath.stackwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("display_name")
    private String displayName;

    private int reputation;

    @JsonProperty("profile_image")
    private String profileImage;

    private String link;
}
