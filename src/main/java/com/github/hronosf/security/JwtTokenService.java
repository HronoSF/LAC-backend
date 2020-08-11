package com.github.hronosf.security;

import com.github.hronosf.model.payload.response.AuthTokensResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {

    AuthTokensResponseDTO createAuthTokens(String username);

    AuthTokensResponseDTO refreshAuthTokens(String token, String refreshToken);

    Authentication getAuthentication(String token);

    boolean isTokenValid(String token);

    String extractToken(HttpServletRequest request);

    UserDetails getUserDetailsByToken(HttpServletRequest request);
}
