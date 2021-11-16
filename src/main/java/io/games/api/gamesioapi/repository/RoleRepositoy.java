package io.games.api.gamesioapi.repository;

import io.games.api.gamesioapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositoy extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
