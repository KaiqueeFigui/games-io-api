package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.dto.request.*;
import io.games.api.gamesioapi.dto.response.AuthResponse;
import io.games.api.gamesioapi.dto.response.UserResponse;
import io.games.api.gamesioapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/activate")
    public ResponseEntity activateAccount(@Valid TokenRequest tokenRequest){
        userService.activateAccount(tokenRequest);
        return ResponseEntity.status(200).build();
    }

    @PutMapping
    public ResponseEntity<UserResponse> putUser(@RequestBody @Valid PutUserRequest putUserRequest){
        return ResponseEntity.status(200).body(userService.putUser(putUserRequest));
    }

    @PostMapping("/password/request-reset")
    public ResponseEntity requestPasswordReset(@RequestBody @Valid PasswordResetRequest passwordResetRequest){
        userService.passwordResetRequest(passwordResetRequest);
        return ResponseEntity.status(200).build();
    }
}
