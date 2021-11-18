package io.games.api.gamesioapi.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CheckUser {

    public static boolean isUserAdmin(Collection<? extends GrantedAuthority> grantedAuthorities){
        List<GrantedAuthority> roles =  grantedAuthorities.stream()
                .filter(authority -> authority.getAuthority().equals(Constants.ROLE_ADMIN)).collect(Collectors.toList());

        return !roles.isEmpty();
    }
}
