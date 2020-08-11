package com.github.hronosf.services.impl;

import com.github.hronosf.exceptions.UserAccountException;
import com.github.hronosf.model.payload.request.LoginRequestDTO;
import com.github.hronosf.model.payload.request.RefreshTokensDTO;
import com.github.hronosf.model.payload.response.AuthTokensResponseDTO;
import com.github.hronosf.security.JwtTokenService;
import com.github.hronosf.services.AuthService;
import com.github.hronosf.services.UserProfileReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenService tokenService;
    private final UserProfileReadService userProfileReadService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthTokensResponseDTO login(LoginRequestDTO request) {
        authorize(request);
        return tokenService.createAuthTokens(request.getEmail());
    }

    @Override
    public AuthTokensResponseDTO refresh(RefreshTokensDTO request) {
        return tokenService.refreshAuthTokens(request.getToken(), request.getRefreshToken());
    }

    private void authorize(LoginRequestDTO request) {
        try {
            if (!userProfileReadService.findByEmail(request.getEmail()).isEnabled()) {
                throw new UserAccountException("Ваш аккаунт ещё не активирован, активируйте его через ссылку в письме, отправленное вам на почту при регистрации.");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Не верные логин или пароль, пожалуйста, проверьте ввод данных!");
        }
    }
}
