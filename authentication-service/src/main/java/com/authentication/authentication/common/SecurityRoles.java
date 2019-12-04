package com.authentication.authentication.common;

public enum SecurityRoles {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String name;

    private SecurityRoles(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
