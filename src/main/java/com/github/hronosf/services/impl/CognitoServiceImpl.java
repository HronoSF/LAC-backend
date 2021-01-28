package com.github.hronosf.services.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.github.hronosf.services.CognitoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CognitoServiceImpl implements CognitoService {

    @Value("${security.userPoolId}")
    private String cognitoPoolId;

    private final AWSCognitoIdentityProvider awsIdentityProvider;

    @Override
    public void createUser(String username, String password, String cognitoGroupName) {
        if (isUsernameTaken(username)) {
            deleteUser(username);
        }

        createCognitoUser(username, password);
        setPermanentPassword(username, password);
        addUserToGroup(username, cognitoGroupName);
    }

    @Override
    public void setPermanentPassword(String username, String password) {
        AdminSetUserPasswordRequest cognitoSetUserPasswordRequest = new AdminSetUserPasswordRequest();
        cognitoSetUserPasswordRequest.setUsername(username);
        cognitoSetUserPasswordRequest.setPassword(password);
        cognitoSetUserPasswordRequest.setUserPoolId(cognitoPoolId);
        cognitoSetUserPasswordRequest.setPermanent(true);
        awsIdentityProvider.adminSetUserPassword(cognitoSetUserPasswordRequest);
    }

    private void createCognitoUser(String username, String password) {
        AdminCreateUserRequest cognitoCreateUserRequest = new AdminCreateUserRequest();
        cognitoCreateUserRequest.setTemporaryPassword(password);
        cognitoCreateUserRequest.setUsername(username);
        cognitoCreateUserRequest.setUserPoolId(cognitoPoolId);
        awsIdentityProvider.adminCreateUser(cognitoCreateUserRequest);
    }

    private void addUserToGroup(String username, String cognitoGroupName) {
        AdminAddUserToGroupRequest adminAddUserToGroupRequest = new AdminAddUserToGroupRequest();
        adminAddUserToGroupRequest.setGroupName(cognitoGroupName);
        adminAddUserToGroupRequest.setUserPoolId(cognitoPoolId);
        adminAddUserToGroupRequest.setUsername(username);
        awsIdentityProvider.adminAddUserToGroup(adminAddUserToGroupRequest);
    }

    private void deleteUser(String username) {
        AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest();
        adminDeleteUserRequest.setUsername(username);
        adminDeleteUserRequest.setUserPoolId(cognitoPoolId);
        awsIdentityProvider.adminDeleteUser(adminDeleteUserRequest);
    }

    private boolean isUsernameTaken(String username) {
        AdminGetUserRequest cognitoGetUserRequest = new AdminGetUserRequest();
        cognitoGetUserRequest.setUsername(username);
        cognitoGetUserRequest.setUserPoolId(cognitoPoolId);
        try {
            awsIdentityProvider.adminGetUser(cognitoGetUserRequest);
            return true;
        } catch (UserNotFoundException userNotFoundException) {
            return false;
        }
    }
}
