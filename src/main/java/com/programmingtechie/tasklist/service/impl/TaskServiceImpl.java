package com.programmingtechie.tasklist.service.impl;

import com.programmingtechie.tasklist.domain.exception.ResourceNotFoundException;
import com.programmingtechie.tasklist.domain.task.Status;
import com.programmingtechie.tasklist.domain.task.Task;
import com.programmingtechie.tasklist.domain.task.TaskImage;
import com.programmingtechie.tasklist.domain.user.User;
import com.programmingtechie.tasklist.repository.TaskRepository;
import com.programmingtechie.tasklist.service.ImageService;
import com.programmingtechie.tasklist.service.TaskService;
import com.programmingtechie.tasklist.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private  final ImageService imageService;



    @Override
    //@Transactional
    @Cacheable(value = "TaskService::getById",key="#task.id")
    public Task create(Task task,Long userId) {
        User user=userService.getById(userId);
        task.setStatus(Status.TODO);
        user.getTasks().add(task);
       userService.update(user);
       return task;
    }

    @Override
    @Transactional
    @Cacheable(value = "TaskService::getById",key = "#id")
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Task not found"));
    }

    @Override
    @Transactional
    public List<Task> getAllByUser(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getById",key = "#task.id")
    public Task update(Task task) {
        if(task.getStatus() == null){
         task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById",key = "#id")
    public void delete(Long id) {
    taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById",key = "#id")
    public void taskImageUpload(Long taskId, TaskImage image) {
        Task task=getById(taskId);
        String fileName=imageService.upload(image);
        task.getImages().add(fileName);
        taskRepository.save(task);
    }


}
