package com.github.hronosf.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_profile_activation_data")
public class ClientAccount {

    @Id
    private String id;

    private String bik;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_corr_acc")
    private String bankCorrAcc;

    private String info;

    @Column(name = "account_number")
    private String accountNumber;

    @Builder.Default
    @Column(name = "created_at")
    private Date createdAtTimeStamp = new Date();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;
}
