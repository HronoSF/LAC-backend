package com.github.hronosf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "client_account_number")
    private String accountNumber;

    @Column(name = "client_address")
    private String clientAddress;
}
