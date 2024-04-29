package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.User;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import io.teamchallenge.woodCrafts.utils.PasswordGeneratorUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User convertUserDtoToUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapper -> mapper.skip(User::setRegistrationDate));
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(userDto.getPassword());

        return user;
    }

    public static UserDto convertUserToUserDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> mapper.skip(UserDto::setPassword));

        return modelMapper.map(user, UserDto.class);
    }
}
