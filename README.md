# Stackwatch Library Documentation

This documentation provides an overview of the **stackwatch** library—a Java-based library for read‑access to the StackExchange API (specifically targeting StackOverflow) using Spring. It simplifies fetching content such as questions, answers, comments, users, and tags, and supports both authenticated and unauthenticated requests.

---

## Table of Contents

1. [Overview](#overview)
2. [Configuration](#configuration)
3. [API Clients](#api-clients)
   - [AbstractStackExchangeClient](#abstractstackexchangeclient)
   - [QuestionClient](#questionclient)
   - [AnswerClient](#answerclient)
   - [CommentClient](#commentclient)
   - [UserClient](#userclient)
   - [TagClient](#tagclient)
4. [Exceptions](#exceptions)
5. [Model Objects](#model-objects)
6. [Service Layer](#service-layer)
7. [Usage Examples](#usage-examples)
8. [Extending and Contributing](#extending-and-contributing)

---

## Overview

**Stackwatch** is a lightweight Java library that provides read‑access to the StackExchange API for StackOverflow data. It offers:
- **Pre-configured API Clients**: Separate clients for questions, answers, comments, users, and tags.
- **Error Handling**: A unified mechanism to convert HTTP errors and API error responses into structured exceptions.
- **Authentication Support**: Built‑in handling for API keys and OAuth access tokens, automatically appending required query parameters.
- **Model Representations**: POJOs mapping directly to the JSON response formats of StackExchange.

The library uses Spring’s `RestTemplate` for HTTP communication and Lombok to reduce boilerplate code.

---

## Configuration

The library leverages Spring configuration to set up properties and the HTTP client.

### StackExchangeProperties

- **Location**: `me.crymath.stackwatch.config.StackExchangeProperties`
- **Purpose**: Holds configuration values such as the API base URL, site parameter (default: `stackoverflow`), API key, and access token.
- **Example Properties**:
  - `apiBaseUrl`: Defaults to `https://api.stackexchange.com/2.3`
  - `site`: Defaults to `stackoverflow`
  - `apiKey`: Your StackExchange API key (optional)
  - `accessToken`: OAuth access token (optional)

### StackExchangeClientConfig

- **Location**: `me.crymath.stackwatch.config.StackExchangeClientConfig`
- **Purpose**: Configures the `RestTemplate` instance used by all API clients.
- **Features**:
  - Sets the base URL for all requests.
  - Registers a query parameter interceptor (`StackExchangeQueryParamInterceptor`) that appends `site`, `key`, and `access_token` to every request.

---

## API Clients

The library organizes API calls into several clients. Each client extends `AbstractStackExchangeClient` for shared error handling.

### AbstractStackExchangeClient

- **Location**: `me.crymath.stackwatch.client.AbstractStackExchangeClient`
- **Purpose**: Provides common logic to execute API calls and convert HTTP errors into structured exceptions.

### QuestionClient

- **Location**: `me.crymath.stackwatch.client.QuestionClient` (and its implementation in `QuestionClientImpl`)
- **Key Methods**:
  - `listQuestions()`: Retrieves a list of recent questions.
  - `getQuestion(int questionId)`: Retrieves a specific question.
  - `listQuestionsByTag(String tag)`: Retrieves questions filtered by a specific tag.

### AnswerClient

- **Location**: `me.crymath.stackwatch.client.AnswerClient` (and its implementation in `AnswerClientImpl`)
- **Key Methods**:
  - `listAnswers()`: Retrieves recent answers.
  - `getAnswer(int answerId)`: Retrieves a specific answer.
  - `listAnswersForQuestion(int questionId)`: Retrieves answers for a given question.

### CommentClient

- **Location**: `me.crymath.stackwatch.client.CommentClient` (and its implementation in `CommentClientImpl`)
- **Key Methods**:
  - `listComments()`: Retrieves recent comments.
  - `getComment(int commentId)`: Retrieves a specific comment.
  - `listCommentsForPost(int postId)`: Retrieves comments for a specific post.

### UserClient

- **Location**: `me.crymath.stackwatch.client.UserClient` (and its implementation in `UserClientImpl`)
- **Key Methods**:
  - `getUser(int userId)`: Retrieves details for a specific user.
  - `getAuthenticatedUser()`: Retrieves details for the authenticated user.
  - `listUsers()`: Lists users (sorted by reputation by default).

### TagClient

- **Location**: `me.crymath.stackwatch.client.TagClient` (and its implementation in `TagClientImpl`)
- **Key Methods**:
  - `listTags()`: Retrieves a list of popular tags.
  - `getTag(String tagName)`: Retrieves detailed information about a specific tag.

---

## Exceptions

The library defines custom exceptions to handle API errors.

### StackExchangeApiException

- **Location**: `me.crymath.stackwatch.exception.StackExchangeApiException`
- **Purpose**: Represents general errors received from the API.

### StackExchangeRateLimitExceededException

- **Location**: `me.crymath.stackwatch.exception.StackExchangeRateLimitExceededException`
- **Purpose**: Indicates that the API rate limit has been exceeded and provides the backoff period.

---

## Model Objects

Stackwatch includes model classes that mirror the JSON structures of the StackExchange API.

- **StackExchangeResponse**: A generic response wrapper.
- **Question**: Represents a StackOverflow question.
- **Answer**: Represents an answer.
- **Comment**: Represents a comment.
- **User**: Represents a user.
- **Tag**: Represents a tag.

---

## Service Layer

### StackExchangeService

- **Location**: `me.crymath.stackwatch.service.StackExchangeService`
- **Purpose**: Aggregates the various API clients into one service facade.
- **Usage**: Provides getters for each client (e.g. `getQuestionClient()`, `getUserClient()`, etc.) so developers can easily access all functionalities.

---

## Usage Examples

### Initializing the StackExchange Service

```java
// Example: Initializing StackExchangeService with a RestTemplate from StackExchangeClientConfig
StackExchangeProperties properties = new StackExchangeProperties();
properties.setApiKey("YOUR_API_KEY_HERE");
properties.setAccessToken("YOUR_ACCESS_TOKEN_HERE"); // Optional
properties.setSite("stackoverflow");

StackExchangeClientConfig config = new StackExchangeClientConfig(properties);
RestTemplate restTemplate = config.stackExchangeRestTemplate();

StackExchangeService stackExchangeService = new StackExchangeService(restTemplate);
```

### Fetching Questions and Users

```java
// Example: Listing recent questions
List<Question> questions = stackExchangeService.getQuestionClient().listQuestions();

// Example: Fetching a specific user
User user = stackExchangeService.getUserClient().getUser(12345);
```

---

# Extending and Contributing

Developers can extend the library by adding new client methods or supporting additional endpoints. The design follows a modular pattern (each client extends `AbstractStackExchangeClient`), making it easy to add new functionalities or adjust error handling as needed.
