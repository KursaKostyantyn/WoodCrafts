package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.utils.AutoFillingTablesUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final AutoFillingTablesUtil autoFillingTablesUtil;
    @GetMapping("/createTestProductLines")
    public void createTestProductLines(){
        autoFillingTablesUtil.autoFilling();
    }
}
