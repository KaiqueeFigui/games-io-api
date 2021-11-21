package io.games.api.gamesioapi.service;

import io.games.api.gamesioapi.dto.request.*;
import io.games.api.gamesioapi.dto.response.AuthResponse;
import io.games.api.gamesioapi.dto.response.UserResponse;

public interface UserService {

    UserResponse postUser(UserRequest userRequest);

    AuthResponse createAuthToken(AuthRequest authRequest);

    void activateAccount(TokenRequest tokenRequest);

    UserResponse putUser(PutUserRequest putUserRequest);

    void passwordResetRequest(PasswordResetRequest passwordResetRequest);
}
