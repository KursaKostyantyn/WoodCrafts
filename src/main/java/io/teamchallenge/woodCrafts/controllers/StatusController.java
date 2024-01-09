package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.services.api.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/statuses")
public class StatusController {

    private final StatusService statusService;
    @GetMapping("/getStatusList")
    public ResponseEntity<List<String>> getStatusList() {
        return statusService.getStatusList();
    }
}
