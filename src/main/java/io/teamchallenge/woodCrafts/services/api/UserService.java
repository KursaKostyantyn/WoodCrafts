package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.UserDto;

public interface UserService {

    void saveUser(UserDto userDto);

    UserDto getUserByEmail(String email);
}
