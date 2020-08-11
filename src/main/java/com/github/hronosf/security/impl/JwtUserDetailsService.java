package com.github.hronosf.security.impl;

import com.github.hronosf.services.UserProfileReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserProfileReadService userProfileReadService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userProfileReadService.findByEmail(email);
    }
}
