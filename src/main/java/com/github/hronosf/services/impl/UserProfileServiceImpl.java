package com.github.hronosf.services.impl;

import com.github.hronosf.domain.User;
import com.github.hronosf.domain.UserAccount;
import com.github.hronosf.domain.UserAccountActivation;
import com.github.hronosf.dto.request.PreTrialAppealRequestDTO;
import com.github.hronosf.dto.request.RequestWithUserDataDTO;
import com.github.hronosf.repository.UserAccountRepository;
import com.github.hronosf.repository.UserRepository;
import com.github.hronosf.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final WhatsAppMessenger whatsAppService;

    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public <T extends RequestWithUserDataDTO> void registerNewUser(T request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            log.debug("User with email {} already exist", request.getPhoneNumber());
            return;
        }

        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .name(buildName(request))
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();

        userRepository.save(newUser);

        UserAccountActivation activationData = whatsAppService.sendVerificationCode(
                newUser, 1000 + new Random().nextInt(8999)
        );
        newUser.setActivationData(activationData);

        if (request instanceof PreTrialAppealRequestDTO) {
            PreTrialAppealRequestDTO preTrialRequest = (PreTrialAppealRequestDTO) request;

            UserAccount newUserAccount = UserAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .bik(preTrialRequest.getConsumerBankBik())
                    .bankName(preTrialRequest.getConsumerBankName())
                    .bankCorrAcc(preTrialRequest.getConsumerBankCorrAcc())
                    .info(preTrialRequest.getConsumerInfo())
                    .accountNumber(preTrialRequest.getCustomerAccountNumber())
                    .user(newUser)
                    .build();

            // save user's bank data:
            userAccountRepository.save(newUserAccount);

            // update user entity with bank data:
            newUser.setBankData(Collections.singletonList(newUserAccount));
        }

        userRepository.save(newUser);
    }

    public void getByPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.getByPhoneNumber(phoneNumber);

        if (user.isPresent()) {

        }
    }

    private String buildName(RequestWithUserDataDTO request) {
        return String.format("%s %s %s",
                request.getFirstName(),
                StringUtils.isNotBlank(request.getMiddleName()) ? request.getMiddleName() : StringUtils.EMPTY,
                request.getLastName())
                .replace("\\s+", StringUtils.EMPTY)
                .trim();
    }
}
