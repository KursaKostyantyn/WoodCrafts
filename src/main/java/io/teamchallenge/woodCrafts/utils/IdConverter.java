package io.teamchallenge.woodCrafts.utils;


public class IdConverter {
    public static String convertIdToString(Long id) {
        return String.format("%06d", id);
    }

    public static Long convertStringToId(String stringId) {
        return Long.parseLong(stringId);
    }
}
