package com.angMetal.orders.entity;


import com.angMetal.orders.enums.StatusFacture;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Facture")
public class FactureVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factureID")
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "facture_products",
            joinColumns = @JoinColumn(name = "factureID"),
            inverseJoinColumns = @JoinColumn(name = "productID")
    )
    private List<Product> products;

    @OneToMany(mappedBy = "facture")
    private Set<Transaction> transactions;
}
