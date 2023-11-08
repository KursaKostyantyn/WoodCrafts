package io.teamchallenge.sosna.constants;

public enum Status {

    PROCESSING("PROCESSING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED");

    final String status;

    public String getStatus() {
        return status;
    }

    Status(String status) {
        this.status = status;
    }
}
