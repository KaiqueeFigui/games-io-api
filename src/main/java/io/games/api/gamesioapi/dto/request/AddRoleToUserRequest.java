package io.games.api.gamesioapi.dto.request;

import io.games.api.gamesioapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleToUserRequest {

    @Email
    private String email;

    @NotBlank
    private String roleName;
}
