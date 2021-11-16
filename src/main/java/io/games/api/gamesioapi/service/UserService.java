package io.games.api.gamesioapi.service;

import io.games.api.gamesioapi.dto.request.AuthRequest;
import io.games.api.gamesioapi.dto.request.UserRequest;
import io.games.api.gamesioapi.dto.response.AuthResponse;
import io.games.api.gamesioapi.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserResponse postUser(UserRequest userRequest);

    AuthResponse createAuthToken(AuthRequest authRequest);
}
