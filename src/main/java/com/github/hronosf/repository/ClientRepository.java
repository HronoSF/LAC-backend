package com.github.hronosf.repository;

import com.github.hronosf.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Client> getByPhoneNumber(String phoneNumber);
}
