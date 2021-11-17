package io.games.api.gamesioapi.dto.request;

import io.games.api.gamesioapi.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {

    @NotBlank
    private String name;

    @NotNull
    private List<Category> categories;
}
