package io.games.api.gamesioapi.dto.request;

import io.games.api.gamesioapi.model.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String subTitle;

    @NotBlank
    @Size(min = 30)
    private String content;

    @NotNull
    private Game game;

}
