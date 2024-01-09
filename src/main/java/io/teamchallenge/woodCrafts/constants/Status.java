package io.teamchallenge.woodCrafts.constants;

import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;

import java.util.Arrays;

public enum Status {

    NEW("Нове"),

    SENT("Відправлене"),

    RECEIVED("Отримане"),
    CANCELLED("Скасоване"),
    PENDING("В обробці");

    final String representationStatus;

    public String getRepresentationStatus() {
        return representationStatus;
    }

    Status(String representationStatus) {
        this.representationStatus = representationStatus;
    }

    public static Status getStatusByRepresentationStatus(String representationStatus) {
        return Arrays.stream(Status.values()).filter(status -> status.representationStatus.equals(representationStatus)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Status '%s' not found", representationStatus)));
    }
}
