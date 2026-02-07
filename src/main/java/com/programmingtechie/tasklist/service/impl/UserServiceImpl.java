package com.programmingtechie.tasklist.service.impl;

import com.programmingtechie.tasklist.domain.exception.ResourceNotFoundException;
import com.programmingtechie.tasklist.domain.user.Role;
import com.programmingtechie.tasklist.domain.user.User;
import com.programmingtechie.tasklist.repository.UserRepository;
import com.programmingtechie.tasklist.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public User getById(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User update(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {

        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username is already in use");
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional
    public boolean isTaskOwner(Long id, Long taskId) {
        return userRepository.isTaskOwner(id, taskId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
     userRepository.delete(id);
    }
}
