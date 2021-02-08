package com.github.hronosf.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    CLIENT(Names.CLIENT),
    ADMIN(Names.ADMIN);

    public static String getPrefix() {
        return "ROLE_";
    }

    private final String name;

    public static class Names {

        public static final String CLIENT = "ROLE_CLIENT";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
