package com.github.hronosf.services;

public interface ShortCodeSenderService {

    void sendCode(String clientPhoneNumber, int accessCode);
}
