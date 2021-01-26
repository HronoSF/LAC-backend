package com.github.hronosf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "client_data")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    private String address;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<ClientBankData> bankData;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private ClientProfileVerification activationData;

    @Builder.Default
    @Column(name = "is_activated")
    private boolean isActivated = false;

    @Builder.Default
    @Column(name = "registration_date")
    private Date registrationDate = new Date();

    @Builder.Default
    @Column(name = "update_at")
    private Date updatedAtDate = new Date();
}
