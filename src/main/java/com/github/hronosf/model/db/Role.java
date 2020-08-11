package com.github.hronosf.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseModel {
    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}