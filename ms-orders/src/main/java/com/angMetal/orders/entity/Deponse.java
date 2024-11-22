package com.angMetal.orders.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deponse")
public class Deponse {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "deponseID")
        private Long deponseID;

        @Column(name = "dateDeponse", nullable = false)
        @Temporal(TemporalType.DATE)
        private Date dateDeponse;

        @Column(name = "montantTotal", nullable = false)
        private Double montantTotal;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fournisseurID", nullable = false)
        private Fournisseur fournisseur;

        @OneToMany(mappedBy = "deponse", cascade = CascadeType.ALL, orphanRemoval = false)
        private List<Transaction> transactions;
}
