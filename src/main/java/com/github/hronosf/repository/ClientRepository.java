package com.github.hronosf.repository;

import com.github.hronosf.model.Client;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface ClientRepository extends UserRepository<Client> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<Client> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);
}
