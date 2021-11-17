package io.games.api.gamesioapi.converter;

import io.games.api.gamesioapi.dto.request.RoleRequest;
import io.games.api.gamesioapi.model.Role;

public interface RoleConverter {

    Role roleRequestoToRole(RoleRequest roleRequest);
}
