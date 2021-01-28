package com.github.hronosf.services;

import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientProfileVerification;

public interface VerificationService {

    ClientProfileVerification sendVerificationCode(Client client);

    void verify(Client client, int verificationCode);

    int generateVerificationCode();
}
