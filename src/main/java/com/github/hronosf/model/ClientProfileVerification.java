package com.github.hronosf.model;

import com.github.hronosf.dto.enums.ActivationCodeStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_profile_activation_data")
public class ClientProfileVerification {

    @Id
    private String id;

    private int code;

    @Builder.Default
    @Column(name = "send_at")
    private Date sendAtTimeStamp = new Date();

    @Builder.Default
    @Column(name = "valid_to")
    private Date validToTimeStamp = new Date();

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    private ActivationCodeStatus status = ActivationCodeStatus.NEW;
}
