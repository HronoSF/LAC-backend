package com.github.hronosf.services;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientProfileVerification;

public interface VerificationService {

    ClientProfileVerification sendVerificationCode(Client client);

    void markVerificationCodeAsUsed(Client client);

    int generateVerificationCode();
}
