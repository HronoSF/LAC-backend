package com.github.hronosf.repository;

import com.github.hronosf.model.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, String> {

    @EntityGraph(attributePaths = {"bankData", "roles"})
    Optional<Client> findByPhoneNumber(String phoneNumber);
}
