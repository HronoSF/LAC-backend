package com.github.hronosf.services.impl;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientProfileVarification;
import com.github.hronosf.enums.ActivationCodeStatus;
import com.github.hronosf.exceptions.ActivationCodeNotValidException;
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
public class WhatsAppTwilioVerificationServiceImpl implements VerificationService {

    private final ClientAccountActivationRepository clientAccountActivationRepository;

    @PostConstruct
    public void initTwilio(@Value("twilio.account.sid") String twilioAccountSid,
                           @Value("twilio.auth.token") String twilioAuthToken) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @Override
    public ClientProfileVarification sendVerificationCode(Client client) {
        int accessCode = generateVerificationCode();

        Date now = new Date();
        ClientProfileVarification activationInfo = ClientProfileVarification.builder()
                .id(UUID.randomUUID().toString())
                .validToTimeStamp(DateUtils.addMinutes(now, 5))
                .client(client)
                .code(accessCode)
                .status(ActivationCodeStatus.NEW)
                .build();

        clientAccountActivationRepository.save(activationInfo);

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + client.getPhoneNumber()),
                new PhoneNumber("whatsapp:+14155238886"),
                "Код верификации: " + accessCode
        ).create();

        log.debug("WhatsApp message {} sent", message);

        activationInfo.setSendAtTimeStamp(now);
        clientAccountActivationRepository.save(activationInfo);

        return activationInfo;
    }

    @Override
    public void markVerificationCodeAsUsed(Client client) {
        ClientProfileVarification activationData = client.getActivationData();

        if (activationData.getStatus().equals(ActivationCodeStatus.NEW) &&
                activationData.getValidToTimeStamp().compareTo(new Date()) >= 0) {

            activationData.setStatus(ActivationCodeStatus.USED);
            clientAccountActivationRepository.save(activationData);
        } else {
            throw new ActivationCodeNotValidException();
        }
    }

    @Override
    public int generateVerificationCode() {
        return 1000 + new Random().nextInt(90000);
    }
}
