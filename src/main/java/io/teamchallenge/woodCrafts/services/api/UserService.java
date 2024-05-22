package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.UserDto;
import io.teamchallenge.woodCrafts.models.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    UserDto getUserByEmail(String email);
    List<User> findAllUsers ();
}
