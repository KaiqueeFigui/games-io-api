package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.config.MyUserDetails;
import io.games.api.gamesioapi.converter.RoleConverter;
import io.games.api.gamesioapi.dto.request.AddRoleToUserRequest;
import io.games.api.gamesioapi.dto.request.RoleRequest;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Role;
import io.games.api.gamesioapi.model.User;
import io.games.api.gamesioapi.repository.RoleRepositoy;
import io.games.api.gamesioapi.repository.UserRepository;
import io.games.api.gamesioapi.service.RoleService;
import io.games.api.gamesioapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.games.api.gamesioapi.utils.CheckUser.isUserAdmin;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepositoy roleRepositoy;
    private final RoleConverter roleConverter;
    private final UserRepository userRepository;

    @Override
    public Role getRoleById(Integer id) {

        Optional<Role> role = roleRepositoy.findById(id);

        if (role.isEmpty()){
            throw new ApiRequestException("Not Found", HttpStatus.NOT_FOUND);
        }

        return role.get();
    }

    @Override
    public Role getRoleByName(String name) {

        Optional<Role> role = roleRepositoy.findByName(name);

        if (role.isEmpty()){
            throw new ApiRequestException("Not Found", HttpStatus.BAD_REQUEST);
        }

        return role.get();
    }

    @Override
    public Role postRole(RoleRequest roleRequest) {

        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (isUserAdmin(userDetails.getAuthorities())){
            throw new ApiRequestException("Access denied", HttpStatus.FORBIDDEN);
        }

        return roleRepositoy.save(roleConverter.roleRequestoToRole(roleRequest));
    }

    @Override
    public void addRoleToUser(AddRoleToUserRequest addRoleToUserRequest) {

        Optional<User> userOptional = userRepository.findByEmail(addRoleToUserRequest.getEmail());
        Optional<Role> roleOptional = roleRepositoy.findByName(addRoleToUserRequest.getRoleName());

        if (userOptional.isEmpty()){
            throw new ApiRequestException("User not found", HttpStatus.NOT_FOUND);
        }else if(roleOptional.isEmpty()){
            throw new ApiRequestException("Role not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        Role role = roleOptional.get();
        List<Role> userRoles = user.getRoles();
        userRoles.add(role);

        user.setRoles(userRoles);
    }
}
