package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.User;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
