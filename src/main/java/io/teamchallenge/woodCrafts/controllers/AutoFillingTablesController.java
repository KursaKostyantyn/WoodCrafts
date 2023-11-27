package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.services.api.AutoFillingTablesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autoFilling")
@AllArgsConstructor
public class AutoFillingTablesController {
    private final AutoFillingTablesService autoFillingTablesService;
    @GetMapping()
    public void autoFilingTables(){
        autoFillingTablesService.autoFillingTables();
    }
}
