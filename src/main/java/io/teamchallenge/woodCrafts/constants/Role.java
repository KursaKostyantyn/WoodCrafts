package io.teamchallenge.woodCrafts.constants;

public enum Role {

    ROLE_ADMIN("ADMIN"),
    ROLE_CUSTOMER("CUSTOMER");
    final String userRole;

    public String getUserRole() {
        return userRole;
    }

    Role(String userRole) {
        this.userRole = userRole;
    }


}
