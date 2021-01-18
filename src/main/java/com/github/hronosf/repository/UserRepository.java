package com.github.hronosf.repository;

import com.github.hronosf.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> getByPhoneNumber(String phoneNumber);
}
