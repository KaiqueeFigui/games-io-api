package io.games.api.gamesioapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserResponse implements Serializable {

    private Integer id;

    private String email;

    private String name;

    private String nickname;
}
