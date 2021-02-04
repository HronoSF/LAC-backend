package com.github.hronosf.model;

import com.github.hronosf.dto.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
public class Document {

    @Id
    private String id;

    @Column(name = "document_name")
    private String documentName;

    private String url;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_bank_data_id", referencedColumnName = "id")
    private ClientBankData clientBankData;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_data_id", referencedColumnName = "id")
    private ProductData productData;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.ORDINAL)
    private DocumentType type;

    @Column(name = "is_saved_before_activation")
    private boolean isSavedBeforeActivation;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
