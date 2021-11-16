package io.games.api.gamesioapi.converter.impl;

import io.games.api.gamesioapi.converter.RoleConverter;
import io.games.api.gamesioapi.dto.request.RoleRequest;
import io.games.api.gamesioapi.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role roleRequestoToRole(RoleRequest roleRequest) {
        return Role.builder()
                .name(roleRequest.getName())
                .build();
    }
}
