package com.github.hronosf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    protected String id;

    @Column(name = "phone_number")
    protected String phoneNumber;
}
