package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.config.MyUserDetails;
import io.games.api.gamesioapi.dto.request.AuthRequest;
import io.games.api.gamesioapi.dto.request.UserRequest;
import io.games.api.gamesioapi.dto.response.AuthResponse;
import io.games.api.gamesioapi.dto.response.UserResponse;
import io.games.api.gamesioapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> postUser(@RequestBody @Valid UserRequest userRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.postUser(userRequest));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> getAuth(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.status(200).body(userService.createAuthToken(authRequest));
    }

}
