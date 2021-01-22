package com.github.hronosf.services;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientProfileVarification;

public interface VerificationService {

    ClientProfileVarification sendVerificationCode(Client client);

    void markVerificationCodeAsUsed(Client client);

    int generateVerificationCode();
}
