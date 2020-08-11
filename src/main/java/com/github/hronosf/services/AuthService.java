package com.github.hronosf.services;

import com.github.hronosf.model.payload.request.LoginRequestDTO;
import com.github.hronosf.model.payload.request.RefreshTokensDTO;
import com.github.hronosf.model.payload.response.AuthTokensResponseDTO;

public interface AuthService {

    AuthTokensResponseDTO login(LoginRequestDTO request);

    AuthTokensResponseDTO refresh(RefreshTokensDTO request);
}
