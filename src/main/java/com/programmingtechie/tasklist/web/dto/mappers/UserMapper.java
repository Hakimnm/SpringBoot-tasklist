package com.programmingtechie.tasklist.web.dto.mappers;

import com.programmingtechie.tasklist.domain.user.User;
import com.programmingtechie.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
