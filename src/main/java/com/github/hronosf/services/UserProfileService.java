package com.github.hronosf.services;

import com.github.hronosf.dto.request.RequestWithUserDataDTO;

public interface UserProfileService {

    <T extends RequestWithUserDataDTO> void registerNewUser(T request);
}
