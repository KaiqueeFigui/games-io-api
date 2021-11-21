package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.config.MyUserDetails;
import io.games.api.gamesioapi.converter.UserConverter;
import io.games.api.gamesioapi.dto.request.*;
import io.games.api.gamesioapi.dto.response.AuthResponse;
import io.games.api.gamesioapi.dto.response.UserResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.mailing.EmailConfig;
import io.games.api.gamesioapi.model.User;
import io.games.api.gamesioapi.repository.UserRepository;
import io.games.api.gamesioapi.service.UserService;
import io.games.api.gamesioapi.utils.Constants;
import io.games.api.gamesioapi.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final EmailConfig emailConfig;

    @Override
    public UserResponse postUser(UserRequest userRequest) {

        checkNewUser(userRequest).ifPresent(message -> {
            throw new ApiRequestException(message, HttpStatus.BAD_REQUEST);
        });

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User user = userConverter.userRequestToUser(userRequest);

        sendMailToActivateAccount(user);

        return userConverter.userToUserResponse(userRepository.save(user));
    }

    private void sendMailToActivateAccount(User user) {

        String message = "Hello " + user.getName() + " your token to activate your account in Games.io is: " + jwtTokenUtil.generateUserToken(user);

        emailConfig.sendEmail(message, "Activate your account", user.getEmail());
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

    @Override
    public void activateAccount(TokenRequest tokenRequest) {

        User user = userRepository.findByEmail(jwtTokenUtil.extractEmail(tokenRequest.getToken()))
                .orElseThrow(() -> {
                    throw new ApiRequestException("User does not exist", HttpStatus.NOT_FOUND);
                });

        if (!jwtTokenUtil.validateUserToken(tokenRequest.getToken(), user)){
            throw new ApiRequestException("Token is not valid", HttpStatus.UNAUTHORIZED);
        }

        user.setActive(true);
    }

    @Override
    public UserResponse putUser(PutUserRequest putUserRequest) {

        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(myUserDetails.getId()).orElseThrow(() -> {
           throw new ApiRequestException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        });

        updateUser(user, putUserRequest);
        sendNotificationMailUpdatedAccount(user);

        return userConverter.userToUserResponse(user);
    }

    @Override
    public void passwordResetRequest(PasswordResetRequest passwordResetRequest) {

        User user = userRepository.findByEmail(passwordResetRequest.getEmail()).orElseThrow(() -> {
            throw new ApiRequestException("User does not exists", HttpStatus.NOT_FOUND);
        });

        sendPasswordResetTokenViaEmail(user);
    }

    private void sendPasswordResetTokenViaEmail(User user) {

        String message = String.format(Constants.RESET_PASSWORD_MESSAGE,
                user.getName(),
                jwtTokenUtil.generateUserToken(user));
        emailConfig.sendEmail(message, Constants.RESET_PASSWORD_SUBJECT, user.getEmail());
    }

    private void sendNotificationMailUpdatedAccount(User user) {

        String message = String.format(
                Constants.UPDATED_ACCOUNT_MESSAGE,
                user.getName(),
                localDateTimeFormat(LocalDateTime.now()));

        emailConfig.sendEmail(message, Constants.UPDATED_ACCOUNT_SUBJECT, user.getEmail());
    }

    private void updateUser(User user, PutUserRequest putUserRequest) {
        user.setEmail(putUserRequest.getEmail());
        user.setName(putUserRequest.getName());
        user.setNickname(putUserRequest.getNickname());
    }

    private String localDateTimeFormat(LocalDateTime localDateTime){

        String hours = localDateTime.toString().substring(
                localDateTime.toString().indexOf("T") + 1 ,localDateTime.toString().indexOf("."));

        return String.format("%s %s",
                localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE),
                hours);
    }
}
