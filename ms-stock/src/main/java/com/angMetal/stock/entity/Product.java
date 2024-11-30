package com.angMetal.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product_index")
public class Product {

    @Id
    private Long productID;
    private String nom;
    private String description;
    private Double prixUnitaire;
    private int quantiteEnStock;
    private Double taxe;

}
