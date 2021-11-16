package io.games.api.gamesioapi.converter;

import io.games.api.gamesioapi.dto.request.UserRequest;
import io.games.api.gamesioapi.dto.response.UserResponse;
import io.games.api.gamesioapi.model.User;
import org.springframework.stereotype.Component;

public interface UserConverter {

    User userRequestToUser(UserRequest userRequest);

    UserResponse userToUserResponse(User user);
}
