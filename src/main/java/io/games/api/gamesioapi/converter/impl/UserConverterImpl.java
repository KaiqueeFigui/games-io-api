package io.games.api.gamesioapi.converter.impl;

import io.games.api.gamesioapi.converter.UserConverter;
import io.games.api.gamesioapi.dto.request.UserRequest;
import io.games.api.gamesioapi.dto.response.UserResponse;
import io.games.api.gamesioapi.model.User;
import io.games.api.gamesioapi.repository.RoleRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {

    private final RoleRepositoy roleRepositoy;

    @Override
    public User userRequestToUser(UserRequest userRequest) {

        return User.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .nickname(userRequest.getNickname())
                .password(userRequest.getPassword())
                .roles(List.of(roleRepositoy.findByName("USER").get()))
                .build();
    }

    @Override
    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
