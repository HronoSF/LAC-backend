package com.github.hronosf.services.impl;

import com.github.hronosf.model.User;
import com.github.hronosf.repository.UserRepository;
import com.github.hronosf.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository<User> userRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends User> T getByPhoneNumber(String phoneNumber) {
        return (T) userRepository.getByPhoneNumber(phoneNumber)
                .orElse(null);
    }
}

