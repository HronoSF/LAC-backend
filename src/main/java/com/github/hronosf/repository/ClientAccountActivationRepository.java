package com.github.hronosf.repository;

import com.github.hronosf.domain.ClientProfileVerification;
import org.springframework.data.repository.CrudRepository;

public interface ClientAccountActivationRepository extends CrudRepository<ClientProfileVerification, String> {
}
