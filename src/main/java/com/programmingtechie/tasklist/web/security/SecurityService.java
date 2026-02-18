package com.programmingtechie.tasklist.web.security;

import com.programmingtechie.tasklist.domain.user.Role;
import com.programmingtechie.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public boolean canAccessUser(Long id, Authentication authentication) {
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        return user.getId().equals(id)
                || authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
    }

    public boolean canAccessTask(Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        var d = userService.isTaskOwner(user.getId(), taskId);
        return d;
    }
}

