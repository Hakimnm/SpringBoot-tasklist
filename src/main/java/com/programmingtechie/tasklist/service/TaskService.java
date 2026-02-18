package com.programmingtechie.tasklist.service;

import com.programmingtechie.tasklist.domain.task.Task;
import com.programmingtechie.tasklist.domain.task.TaskImage;

import java.util.List;

public interface TaskService {
    Task getById(Long id);

    List<Task> getAllByUser(Long id);

    Task update(Task task);

    Task create(Task task, Long id);

    void delete(Long id);

    void taskImageUpload(Long taskId, TaskImage image);
}
