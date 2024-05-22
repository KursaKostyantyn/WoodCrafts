package io.teamchallenge.woodCrafts.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdConverterTest {

    @Test
    void convertIdToString() {
        Long id = 1L;
        String expectedResult = "000001";
        String actualResult = IdConverter.convertIdToString(id);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void convertStringToId() {
        String formattedId = "000001";
        Long expectedResult = 1L;
        Long actualResult = IdConverter.convertStringToId(formattedId);
        assertEquals(expectedResult, actualResult);

    }
}