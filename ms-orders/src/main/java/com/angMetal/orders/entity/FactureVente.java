package com.angMetal.orders.entity;

import com.angMetal.orders.enums.StatusFacture;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facture_vente")
public class FactureVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facture_id")
    private Long factureID;

    @Column(name = "dateEmission", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Emission date is required.")
    private Date dateEmission;

    @Column(name = "dateEcheance", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Echeance date is required.")
    private Date dateEcheance;

    @Column(name = "montantTotal", nullable = false)
    @NotNull(message = "Total amount is required.")
    private Double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20)
    private StatusFacture statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientID")
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "facture_vente_product", // This is the join table
            joinColumns = @JoinColumn(name = "facture_id"), // Foreign key for FactureVente
            inverseJoinColumns = @JoinColumn(name = "product_id") // Foreign key for Product
    )
    private List<Product> products; // List of associated products
}
