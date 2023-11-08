package io.teamchallenge.sosna.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.teamchallenge.sosna.models.dto.UserDto;
import io.teamchallenge.sosna.services.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> saveUser(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RateLimiter(name = "basic-actions")
    @GetMapping("/getUserByEmail")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
    }


}
