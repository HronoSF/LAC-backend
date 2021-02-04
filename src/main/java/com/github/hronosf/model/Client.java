package com.github.hronosf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "Client.detailed",
        attributeNodes = {@NamedAttributeNode("documents"), @NamedAttributeNode("roles")}
)
@DiscriminatorValue("CLIENT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Client extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private ClientProfileVerification activationData;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @Column(name = "is_activated")
    private boolean isActivated;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;
}
