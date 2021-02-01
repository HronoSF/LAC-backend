package com.github.hronosf.repository;

import com.github.hronosf.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {

    Role getByName(String name);
}
