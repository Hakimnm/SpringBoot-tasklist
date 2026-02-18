package com.programmingtechie.tasklist.service.impl;

import com.programmingtechie.tasklist.domain.user.User;
import com.programmingtechie.tasklist.service.AuthService;
import com.programmingtechie.tasklist.service.UserService;
import com.programmingtechie.tasklist.web.dto.auth.JwtRequest;
import com.programmingtechie.tasklist.web.dto.auth.JwtResponse;
import com.programmingtechie.tasklist.web.security.JwtTokenProvedir;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvedir jwtTokenProvedir;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setAccessToken(jwtTokenProvedir.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvedir.createRefreshToken(user.getId(), user.getUsername()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvedir.refreshUserToken(refreshToken);
    }
}
