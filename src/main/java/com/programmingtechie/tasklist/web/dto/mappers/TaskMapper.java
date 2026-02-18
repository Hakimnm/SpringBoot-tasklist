package com.programmingtechie.tasklist.web.dto.mappers;

import com.programmingtechie.tasklist.domain.task.Task;
import com.programmingtechie.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {


}
