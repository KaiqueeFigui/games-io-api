package io.games.api.gamesioapi.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse implements Serializable {

    private String jwt;
}
