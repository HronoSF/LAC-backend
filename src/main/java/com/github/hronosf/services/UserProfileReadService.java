package com.github.hronosf.services;

import com.github.hronosf.model.db.User;

public interface UserProfileReadService {

    User findByEmail(String email);
}
