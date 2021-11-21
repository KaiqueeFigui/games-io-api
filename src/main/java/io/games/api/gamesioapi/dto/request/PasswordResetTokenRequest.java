package io.games.api.gamesioapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PasswordResetTokenRequest {

    @NotBlank
    private String token;

    @Size(min = 8)
    private String password;
}
