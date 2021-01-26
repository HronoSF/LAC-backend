package com.github.hronosf.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_bank_data")
public class ClientBankData {

    @Id
    private String id;

    private String bik;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_corr_acc")
    private String bankCorrAcc;

    @Column(name = "account_number")
    private String accountNumber;

    @Builder.Default
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
