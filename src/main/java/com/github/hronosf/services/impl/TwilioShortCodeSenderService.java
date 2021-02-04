package com.github.hronosf.services.impl;

import com.github.hronosf.services.ShortCodeSenderService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwilioShortCodeSenderService implements ShortCodeSenderService {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${verification.code.timeout.minutes}")
    private int twilioVerificationCodeTimeout;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @Override
    public void sendCode(String clientPhoneNumber, int accessCode) {
        Message.creator(
                new PhoneNumber(StringUtils.EMPTY + clientPhoneNumber),
                new PhoneNumber(StringUtils.EMPTY + twilioPhoneNumber),
                "Ваш код верификации: " + accessCode + " действителен в течении "
                        + twilioVerificationCodeTimeout + " минут(ы)"
        ).create();
    }
}
