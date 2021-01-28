package com.github.hronosf.services.impl;

import com.github.hronosf.dto.enums.ActivationCodeStatus;
import com.github.hronosf.exceptions.ActivationCodeNotValidException;
import com.github.hronosf.exceptions.ActivationCodeStillValidException;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientProfileVerification;
import com.github.hronosf.repository.ClientAccountActivationRepository;
import com.github.hronosf.services.VerificationService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwilioVerificationServiceImpl implements VerificationService {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.verification.code.timeout.minutes}")
    private int twilioVerificationCodeTimeout;

    private final ClientAccountActivationRepository clientAccountActivationRepository;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @Override
    public ClientProfileVerification sendVerificationCode(Client client) {
        int accessCode = generateVerificationCode();

        Date now = new Date();

        ClientProfileVerification activationInfo;

        if (client.getActivationData() != null) {

            if (isVerificationTokenExpired(client.getActivationData())) {
                activationInfo = client.getActivationData()
                        .setCode(accessCode)
                        .setValidToTimeStamp(DateUtils.addMinutes(now, twilioVerificationCodeTimeout))
                        .setStatus(ActivationCodeStatus.NEW);
            } else {
                throw new ActivationCodeStillValidException();
            }

        } else {
            activationInfo = ClientProfileVerification.builder()
                    .id(UUID.randomUUID().toString())
                    .validToTimeStamp(DateUtils.addMinutes(now, twilioVerificationCodeTimeout))
                    .client(client)
                    .code(accessCode)
                    .status(ActivationCodeStatus.NEW)
                    .build();
        }

        clientAccountActivationRepository.save(activationInfo);

// whatsapp:
//        Message message = Message.creator(
//                new PhoneNumber("whatsapp:" + client.getPhoneNumber()),
//                new PhoneNumber("whatsapp:+14155238886"),
//                "Код верификации: " + accessCode
//        ).create();

        Message.creator(
                new PhoneNumber(client.getPhoneNumber()),
                new PhoneNumber(twilioPhoneNumber),
                "Ваш код верификации: " + accessCode
        ).create();

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
