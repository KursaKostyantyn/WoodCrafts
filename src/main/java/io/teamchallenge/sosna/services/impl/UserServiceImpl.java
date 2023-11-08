package io.teamchallenge.sosna.services.impl;

import io.teamchallenge.sosna.mapper.UserMapper;
import io.teamchallenge.sosna.models.User;
import io.teamchallenge.sosna.models.dto.UserDto;
import io.teamchallenge.sosna.repository.UserRepository;
import io.teamchallenge.sosna.services.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void saveUser(UserDto userDto) {
        User user = UserMapper.convertUserDtoToUser(userDto);

        userRepository.save(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);

        return UserMapper.convertUserToUserDto(user);
    }
}
