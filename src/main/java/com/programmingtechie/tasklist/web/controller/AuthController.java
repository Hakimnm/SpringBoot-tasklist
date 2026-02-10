package com.programmingtechie.tasklist.web.controller;

import com.programmingtechie.tasklist.domain.user.User;
import com.programmingtechie.tasklist.service.AuthService;
import com.programmingtechie.tasklist.service.UserService;
import com.programmingtechie.tasklist.web.dto.auth.JwtRequest;
import com.programmingtechie.tasklist.web.dto.auth.JwtResponse;
import com.programmingtechie.tasklist.web.dto.mappers.UserMapper;
import com.programmingtechie.tasklist.web.dto.user.UserDto;
import com.programmingtechie.tasklist.web.dto.validation.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Validated
@Tag(name = "Auth Controller",description = "Auth API")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
  private final UserMapper userMapper;


    @PostMapping("/login")
    @Operation(summary = "login operation")
    public JwtResponse login(@Validated @RequestBody JwtRequest request) {
        var d= authService.login(request);
        return d;
    }

    @PostMapping("/registr")
    @Operation(summary = "registr operation")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
   User user=userMapper.toEntity(userDto);
   User createdUser=userService.create(user);
   var dt= userMapper.toDto(createdUser);
   return dt;
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh operation")
    public JwtResponse refresh( @RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
