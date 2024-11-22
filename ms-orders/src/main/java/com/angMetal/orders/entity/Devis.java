package com.angMetal.orders.entity;


import com.angMetal.orders.enums.StatusDevis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quoteID")
    private Integer quoteID;

    @Column(name = "dateCreation", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Creation date is required.")
    private Date dateCreation;

    @Column(name = "dateExpiration", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Expiration date is required.")
    private Date dateExpiration;

    @Column(name = "montantTotal", nullable = false)
    @NotNull(message = "Total amount is required.")
    private Double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusDevis statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientID", nullable = false)
    private Client client;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "devis_products",
            joinColumns = @JoinColumn(name = "quoteID"),
            inverseJoinColumns = @JoinColumn(name = "productID")
    )
    private List<Product> products;
}
