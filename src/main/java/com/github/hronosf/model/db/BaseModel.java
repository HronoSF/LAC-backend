package com.github.hronosf.model.db;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_timestamp")
    private Date createdTimestamp;

    @LastModifiedDate
    @Column(name = "updated_timestamp")
    private Date updatedTimestamp;

    @LastModifiedDate
    @Column(name = "deleted_timestamp")
    private Date deletedTimestamp;
}
