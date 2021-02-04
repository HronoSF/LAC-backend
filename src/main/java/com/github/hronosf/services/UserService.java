package com.github.hronosf.services;

import com.github.hronosf.model.User;

public interface UserService {

    <T extends User> T getByPhoneNumber(String phoneNumber);
}
