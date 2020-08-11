package com.github.hronosf.model.payload.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String password;
}
