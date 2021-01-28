package com.github.hronosf.dto;

import com.github.hronosf.dto.enums.Permissions;
import com.github.hronosf.dto.enums.Roles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RoleToPermissionMapping {

    public static final Map<String, List<Permissions>> ROLES_TO_PERMISSIONS = new HashMap<>();

    static {
        // client:
        ROLES_TO_PERMISSIONS.put(
                Roles.CLIENT.getName(),
                Arrays.asList(Permissions.GENERATE_POST_INVENTORY,
                        Permissions.GENERATE_PRETRIAL,
                        Permissions.CAN_LOAD_SAVED_BANK_DATA)
        );

        // admin:
        ROLES_TO_PERMISSIONS.put(
                Roles.ADMIN.getName(),
                Arrays.asList(Permissions.VIEW_ALL_CLIENTS, Permissions.VIEW_DOCUMENTS_OF_ALL_CLIENT,
                        Permissions.GENERATE_POST_INVENTORY, Permissions.GENERATE_PRETRIAL,
                        Permissions.CAN_LOAD_SAVED_BANK_DATA)
        );
    }
}
