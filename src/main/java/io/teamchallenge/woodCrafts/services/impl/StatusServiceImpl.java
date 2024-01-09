package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.services.api.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {
    @Override
    public ResponseEntity<List<String>> getStatusList() {
        List<String> statusList =new ArrayList<>();
        Arrays.stream(Status.values()).forEach(status -> statusList.add(status.getRepresentationStatus()));
        return ResponseEntity.status(HttpStatus.OK).body(statusList);
    }
}
