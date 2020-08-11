package com.github.hronosf.model.payload.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    private String email;
    private String password;
}
