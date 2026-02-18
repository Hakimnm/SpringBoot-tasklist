package com.programmingtechie.tasklist.web.dto.mappers;

import com.programmingtechie.tasklist.domain.task.TaskImage;
import com.programmingtechie.tasklist.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {


}
