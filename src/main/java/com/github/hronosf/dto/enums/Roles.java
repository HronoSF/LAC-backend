package com.github.hronosf.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    CLIENT("ROLE_client"),
    ADMIN("ROLE_admin");

    public static String getPrefix() {
        return "ROLE_";
    }

    private final String name;
}
