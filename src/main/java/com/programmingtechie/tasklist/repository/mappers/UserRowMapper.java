package com.programmingtechie.tasklist.repository.mappers;

import com.programmingtechie.tasklist.domain.task.Task;
import com.programmingtechie.tasklist.domain.user.Role;
import com.programmingtechie.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {
    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            roles.add(Role.valueOf(resultSet.getString("user_role_role")));
        }
        resultSet.beforeFirst();
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("user_password"));
            user.setUsername(resultSet.getString("user_username"));
            user.setRoles(roles);
            user.setTasks(tasks);
            return user;
        }

return null;
    }
}
