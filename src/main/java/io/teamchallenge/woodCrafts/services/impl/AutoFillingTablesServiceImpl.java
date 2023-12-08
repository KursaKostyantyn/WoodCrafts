package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.services.api.AutoFillingTablesService;
import io.teamchallenge.woodCrafts.utils.AutoFillingTables;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AutoFillingTablesServiceImpl implements AutoFillingTablesService {

    private final AutoFillingTables autoFillingTables;

    @Override
    public void autoFillingTables(int numberOfProducts) {
        autoFillingTables.autoFilling(numberOfProducts);
    }
}
