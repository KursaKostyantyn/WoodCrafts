package io.teamchallenge.woodCrafts.services.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StatusService {
    ResponseEntity<List<String>> getStatusList();
}
