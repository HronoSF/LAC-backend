package com.github.hronosf.services.impl;

import com.github.hronosf.model.db.User;
import com.github.hronosf.services.UserProfileReadService;
import com.github.hronosf.services.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileReadServiceImpl implements UserProfileReadService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with email %s found", email)));
    }
}
