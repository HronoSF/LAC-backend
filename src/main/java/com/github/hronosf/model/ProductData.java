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
@Table(name = "product_data")
public class ProductData {

    @Id
    private String id;

    @Column(name = "purchase_data")
    private String purchaseData;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_address")
    private String sellerAddress;

    @Column(name = "seller_inn")
    private String sellerINN;
}
