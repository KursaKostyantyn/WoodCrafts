package io.teamchallenge.woodCrafts.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import io.teamchallenge.woodCrafts.services.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<Void> saveUser(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RateLimiter(name = "basic-actions")
    @GetMapping("/users/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
    }


}
