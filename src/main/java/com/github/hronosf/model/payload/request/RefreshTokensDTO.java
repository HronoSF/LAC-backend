package com.github.hronosf.model.payload.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokensDTO {

    private String token;
    private String refreshToken;
}
