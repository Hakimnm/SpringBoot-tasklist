package com.programmingtechie.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.programmingtechie.tasklist.web.dto.validation.OnCreate;
import com.programmingtechie.tasklist.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
@Schema(description = "user DTO")
public class UserDto {

    @NotNull(message = "Id must be not null. ",groups = OnUpdate.class)
    @Schema(description = "user id",example = "1")
    private Long id;

    @Schema(description = "user name",example = "John DOE")
    @NotNull(message = "Name must be not null. ",groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255,message = "Name length must be smaller than 255 symbols",groups = {OnUpdate.class, OnCreate.class})
    private String name;

    @Schema(description = "user username",example = "test@gmail.com")
    @NotNull(message = "Username must be not null. ",groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255,message = "Username length must be smaller than 255 symbols",groups = {OnUpdate.class, OnCreate.class})
    private String username;

    @Schema(description = "user password",example = "$2a$12$EOThIiEbXBvyFoEckMiqpeh0B76tPhY6M0ypTKP5m/ijw2/Q8zb9G")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null. ",groups = {OnUpdate.class, OnCreate.class})
    private String password;

    @Schema(description = "user password confirmation",example = "$2a$12$EOThIiEbXBvyFoEckMiqpeh0B76tPhY6M0ypTKP5m/ijw2/Q8zb9G")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null. ",groups = OnCreate.class)
    private String passwordConfirmation;
}
