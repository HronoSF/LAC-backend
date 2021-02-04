package com.github.hronosf.services.impl;

import com.github.hronosf.dto.enums.ActivationCodeStatus;
import com.github.hronosf.exceptions.ActivationCodeNotValidException;
import com.github.hronosf.exceptions.ActivationCodeStillValidException;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientProfileVerification;
import com.github.hronosf.repository.ClientAccountActivationRepository;
import com.github.hronosf.services.ShortCodeSenderService;
import com.github.hronosf.services.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    @Value("${verification.code.timeout.minutes}")
    private int verificationCodeTimeout;

    private final ShortCodeSenderService shortCodeSenderService;
    private final ClientAccountActivationRepository clientAccountActivationRepository;

    @Override
    public ClientProfileVerification sendVerificationCode(Client client) {
        int accessCode = generateVerificationCode();

        Date now = new Date();

        ClientProfileVerification activationInfo;

        if (client.getActivationData() != null) {

            if (isVerificationTokenExpired(client.getActivationData())) {
                activationInfo = client.getActivationData()
                        .setCode(accessCode)
                        .setValidToTimeStamp(DateUtils.addMinutes(now, verificationCodeTimeout))
                        .setStatus(ActivationCodeStatus.NEW);
            } else {
                throw new ActivationCodeStillValidException();
            }

        } else {
            activationInfo = ClientProfileVerification.builder()
                    .id(UUID.randomUUID().toString())
                    .validToTimeStamp(DateUtils.addMinutes(now, verificationCodeTimeout))
                    .client(client)
                    .code(accessCode)
                    .status(ActivationCodeStatus.NEW)
                    .build();
        }

        clientAccountActivationRepository.save(activationInfo);

        shortCodeSenderService.sendCode(client.getPhoneNumber(), accessCode);

        activationInfo.setSendAtTimeStamp(now);
        clientAccountActivationRepository.save(activationInfo);

        return activationInfo;
    }

    @Override
    public void verify(Client client, int verificationCode) {
        ClientProfileVerification activationData = client.getActivationData();

        if (activationData.getStatus().equals(ActivationCodeStatus.NEW) &&
                !isVerificationTokenExpired(activationData) &&
                client.getActivationData().getCode() == verificationCode) {

            activationData.setStatus(ActivationCodeStatus.USED);
            clientAccountActivationRepository.save(activationData);

        } else {
            throw new ActivationCodeNotValidException();
        }
    }

    @Override
    public int generateVerificationCode() {
        return 1000 + new Random().nextInt(9000);
    }

    private boolean isVerificationTokenExpired(ClientProfileVerification activationData) {
        return activationData.getValidToTimeStamp().compareTo(new Date()) < 0;
    }
}
