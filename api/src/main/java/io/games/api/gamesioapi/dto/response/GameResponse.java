package io.games.api.gamesioapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse implements Serializable {

    private Integer id;

    private String name;

    private List<CategoryResponse> categories;
}
