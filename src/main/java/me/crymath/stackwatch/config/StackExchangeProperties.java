package me.crymath.stackwatch.config;

import lombok.Getter;
import lombok.Setter;

/** Configuration properties for StackWatch (StackOverflow API). */
@Getter
@Setter
public class StackExchangeProperties {
    private String apiBaseUrl = "https://api.stackexchange.com/2.3";
    private String site = "stackoverflow";
    private String apiKey; // Optional – increases quota if provided
    private String accessToken; // Optional – for authenticated requests
}
