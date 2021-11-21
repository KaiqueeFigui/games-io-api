package io.games.api.gamesioapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PasswordResetRequest {

    @Email
    private String email;
}
