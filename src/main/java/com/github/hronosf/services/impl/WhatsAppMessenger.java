package com.github.hronosf.services.impl;

import com.github.hronosf.domain.User;
import com.github.hronosf.domain.UserAccountActivation;
import com.github.hronosf.repository.UserAccountActivationRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsAppMessenger {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    private final UserAccountActivationRepository userAccountActivationRepository;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public UserAccountActivation sendVerificationCode(User user, int accessCode) {
        Date now = new Date();
        UserAccountActivation activationData = UserAccountActivation.builder()
                .id(UUID.randomUUID().toString())
                .validToTimeStamp(DateUtils.addMinutes(now, 5))
                .user(user)
                .code(accessCode)
                .build();

        userAccountActivationRepository.save(activationData);

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + user.getPhoneNumber()),
                new PhoneNumber("whatsapp:+14155238886"),
                "Код активации аккаунта: " + accessCode)
                .create();
        log.debug("WhatsApp message {} sent", message);


        activationData.setSendAtTimeStamp(now);
        userAccountActivationRepository.save(activationData);

        return activationData;
    }

}
