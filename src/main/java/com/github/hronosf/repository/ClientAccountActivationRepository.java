package com.github.hronosf.repository;

import com.github.hronosf.model.ClientProfileVerification;
import org.springframework.data.repository.CrudRepository;

public interface ClientAccountActivationRepository extends CrudRepository<ClientProfileVerification, String> {
}
