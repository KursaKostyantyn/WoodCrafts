package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.mapper.UserMapper;
import io.teamchallenge.woodCrafts.models.User;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import io.teamchallenge.woodCrafts.repository.UserRepository;
import io.teamchallenge.woodCrafts.services.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
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

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
