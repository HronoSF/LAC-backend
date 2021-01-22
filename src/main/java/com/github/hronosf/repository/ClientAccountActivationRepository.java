package com.github.hronosf.repository;

import com.github.hronosf.domain.ClientProfileVarification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAccountActivationRepository extends JpaRepository<ClientProfileVarification, String> {
}
