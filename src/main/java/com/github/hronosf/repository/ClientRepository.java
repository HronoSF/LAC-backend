package com.github.hronosf.repository;

import com.github.hronosf.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, String> {

    Optional<Client> findByPhoneNumber(String phoneNumber);
}
