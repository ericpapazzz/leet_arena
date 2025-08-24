package com.example.leetarena.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer product_id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Integer product_price;

    @Column(name = "product_img")
    private String product_img;

    @Column(name = "product_description")
    private String product_description;

    @Column(name = "product_tag")
    private String product_tag;
}
