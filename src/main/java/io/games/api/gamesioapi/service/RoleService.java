package io.games.api.gamesioapi.service;

import io.games.api.gamesioapi.dto.request.AddRoleToUserRequest;
import io.games.api.gamesioapi.dto.request.RoleRequest;
import io.games.api.gamesioapi.model.Role;

public interface RoleService {

    Role getRoleById(Integer id);

    Role getRoleByName(String name);

    Role postRole(RoleRequest roleRequest);

    void addRoleToUser(AddRoleToUserRequest addRoleToUserRequest);
}
