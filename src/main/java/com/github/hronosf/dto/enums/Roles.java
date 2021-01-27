package com.github.hronosf.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    CLIENT("ROLE_client"),
    ADMIN("ROLE_admin");

    private static final String prefix = "ROLE";

    private final String name;
}
