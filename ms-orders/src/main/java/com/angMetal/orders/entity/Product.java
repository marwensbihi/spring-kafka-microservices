package com.angMetal.orders.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productID;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;

    @Column(name = "quantite_en_stock", nullable = false)
    private int quantiteEnStock;

    @Column(name = "taxe", nullable = false)
    private Double taxe;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Devis> devis;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<FactureAchat> factureAchats;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<FactureVente> factureVentes;

    public Product(Long productID, String name, String description, Double prixUnitaire, int quantiteEnStock, Double taxe) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.taxe = taxe;
    }
}
