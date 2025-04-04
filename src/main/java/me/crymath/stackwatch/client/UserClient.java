package me.crymath.stackwatch.client;

import java.util.List;
import me.crymath.stackwatch.model.User;

public interface UserClient {
    User getUser(int userId);

    User getAuthenticatedUser();

    List<User> listUsers();
}
