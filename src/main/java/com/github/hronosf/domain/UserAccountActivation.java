package com.github.hronosf.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile_activation")
public class UserAccountActivation {

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
