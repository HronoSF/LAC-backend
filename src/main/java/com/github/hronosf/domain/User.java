package com.github.hronosf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "user_data")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String address;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAccount> bankData;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserAccountActivation activationData;

    @Builder.Default
    @Column(name = "is_activated")
    private boolean isActivated = false;
}
