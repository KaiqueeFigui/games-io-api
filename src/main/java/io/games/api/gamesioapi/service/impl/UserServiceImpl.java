package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.converter.UserConverter;
import io.games.api.gamesioapi.dto.request.AuthRequest;
import io.games.api.gamesioapi.dto.request.UserRequest;
import io.games.api.gamesioapi.dto.response.AuthResponse;
import io.games.api.gamesioapi.dto.response.UserResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.User;
import io.games.api.gamesioapi.repository.UserRepository;
import io.games.api.gamesioapi.service.UserService;
import io.games.api.gamesioapi.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponse postUser(UserRequest userRequest) {

        checkNewUser(userRequest).ifPresent(message -> {
            throw new ApiRequestException(message, HttpStatus.BAD_REQUEST);
        });

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User user = userConverter.userRequestToUser(userRequest);

        return userConverter.userToUserResponse(userRepository.save(user));
    }

    private Optional<String> checkNewUser(UserRequest userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()){
            return Optional.of("Email already in use");
        }

        if (userRepository.findByNickname(userRequest.getNickname()).isPresent()){
            return Optional.of("Nickname already in use");
        }

        return Optional.empty();
    }

    @Override
    public AuthResponse createAuthToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new ApiRequestException("Incorrect email or password", HttpStatus.BAD_REQUEST);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(jwt);
    }
}
