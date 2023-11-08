package io.teamchallenge.sosna.services.api;

import io.teamchallenge.sosna.models.dto.UserDto;

public interface UserService {

    void saveUser(UserDto userDto);

    UserDto getUserByEmail(String email);
}
