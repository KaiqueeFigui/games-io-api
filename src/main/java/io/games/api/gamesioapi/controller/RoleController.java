package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.dto.request.AddRoleToUserRequest;
import io.games.api.gamesioapi.dto.request.RoleRequest;
import io.games.api.gamesioapi.model.Role;
import io.games.api.gamesioapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> postRole(
            @RequestBody @Valid RoleRequest roleRequest){

        return ResponseEntity.status(201).body(roleService.postRole(roleRequest));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(roleService.getRoleById(id));
    }


    @GetMapping("/by-name/{name}")
    public ResponseEntity<Role> getRoleById(@PathVariable String name){
        return ResponseEntity.status(200).body(roleService.getRoleByName(name));
    }

    @PutMapping("/role-to-user")
    public ResponseEntity postRoleToUser(@RequestBody AddRoleToUserRequest addRoleToUserRequest){

        roleService.addRoleToUser(addRoleToUserRequest);

        return ResponseEntity.status(200).build();
    }
}
