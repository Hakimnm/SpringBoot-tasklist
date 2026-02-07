package com.programmingtechie.tasklist.service;

import com.programmingtechie.tasklist.domain.user.User;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
    User update(User user);
    User create(User user);
    boolean isTaskOwner(Long id, Long taskId);
    void delete(Long id);

}
