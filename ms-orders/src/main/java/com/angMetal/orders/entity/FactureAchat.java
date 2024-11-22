package com.angMetal.orders.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "factures_achat")
public class FactureAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billID")
    private Long billID;

    @Column(name = "montant_total", nullable = false)
    private double montantTotal;

    @Column(name = "date_emission", nullable = false)
    private LocalDate dateEmission;

    @Column(name = "date_echeance", nullable = false)
    private LocalDate dateEcheance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fournisseurID", nullable = false)
    private Fournisseur fournisseur;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "facture_achat_products",
            joinColumns = @JoinColumn(name = "billID"),
            inverseJoinColumns = @JoinColumn(name = "productID")
    )
    private List<Product> products;
}
