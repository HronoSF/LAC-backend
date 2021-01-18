package com.github.hronosf.repository;

import com.github.hronosf.domain.UserAccountActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountActivationRepository extends JpaRepository<UserAccountActivation, String> {
}
