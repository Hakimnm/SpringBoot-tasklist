package com.programmingtechie.tasklist.web.controller;

import com.programmingtechie.tasklist.domain.task.Task;
import com.programmingtechie.tasklist.service.TaskService;
import com.programmingtechie.tasklist.web.dto.mappers.TaskMapper;
import com.programmingtechie.tasklist.web.dto.task.TaskDto;
import com.programmingtechie.tasklist.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Operation(summary = "task find get by id")
    @GetMapping("/{id}")
    @PreAuthorize("@customSecurtiyExpression.canAccessTask(#id)")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @Operation(summary = "delete task")
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurtiyExpression.canAccessTask(#id)")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @Operation(summary = "update task")
    @PutMapping
    @PreAuthorize("@customSecurtiyExpression.canAccessTask(#id)")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);

    }

}
