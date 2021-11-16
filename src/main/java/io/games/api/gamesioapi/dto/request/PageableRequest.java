package io.games.api.gamesioapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableRequest {

    @NotNull
    @Min(-1)
    private Integer pageNum;

    @NotNull
    @Min(1)
    private Integer pageSize;

}
