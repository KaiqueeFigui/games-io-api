package io.games.api.gamesioapi.dto.response;

import io.games.api.gamesioapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse implements Serializable {

    private Integer id;

    private String title;

    private String subTitle;

    private String content;

    private UserResponse author;

    private GameResponse game;
}
