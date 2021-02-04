package com.github.hronosf.repository;

import com.github.hronosf.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository<T extends User> extends CrudRepository<T, String> {

    Optional<T> getByPhoneNumber(String phoneNumber);
}
