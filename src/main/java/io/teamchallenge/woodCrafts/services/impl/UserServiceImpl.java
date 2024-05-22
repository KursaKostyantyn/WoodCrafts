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
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void saveUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);

        userRepository.save(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);

        return userMapper.userToUserDto(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
