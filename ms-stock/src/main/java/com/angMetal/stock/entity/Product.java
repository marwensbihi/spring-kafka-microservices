package com.angMetal.stock.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product_stock")
public class Product {

    @Id
    private Long productID;

    private String nom;
    private String description;
    private Double prixUnitaire;
    private int quantiteEnStock;
    private Double taxe;

    // Getters and setters
}
