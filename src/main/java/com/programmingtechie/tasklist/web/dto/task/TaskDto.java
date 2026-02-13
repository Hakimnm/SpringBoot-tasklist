package com.programmingtechie.tasklist.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.programmingtechie.tasklist.domain.task.Status;
import com.programmingtechie.tasklist.web.dto.validation.OnCreate;
import com.programmingtechie.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {

    @NotNull(message = "Id must be not null. ",groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must be not null. ",groups={OnUpdate.class, OnCreate.class})
    @Length(max = 255,message = "Title length must be smaller than 255 symbols",groups ={ OnUpdate.class,OnCreate.class})
    private String title;

    @Length(max = 255,message = "Description length must be smaller than 255 symbols",groups ={ OnUpdate.class,OnCreate.class})
    private String description;

    private Status status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime expirationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;
}
