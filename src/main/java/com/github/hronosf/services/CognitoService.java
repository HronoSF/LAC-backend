package com.github.hronosf.services;

public interface CognitoService {

    void createUser(String username, String password, String cognitoGroupName);

    void setPermanentPassword(String username, String password);

    void deleteUser(String username);
}
