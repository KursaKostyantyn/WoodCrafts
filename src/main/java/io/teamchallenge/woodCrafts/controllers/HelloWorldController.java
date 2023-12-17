package io.teamchallenge.woodCrafts.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@AllArgsConstructor
public class HelloWorldController {

    @GetMapping("")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello! This is online-shop woodCrafts");
    }
}
