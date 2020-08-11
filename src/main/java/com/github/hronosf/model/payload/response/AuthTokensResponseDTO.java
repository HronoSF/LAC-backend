package com.github.hronosf.model.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokensResponseDTO {

    private final String token;
    private final String refreshToken;
}
