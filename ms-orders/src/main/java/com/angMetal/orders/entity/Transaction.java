package com.angMetal.orders.entity;

import javax.persistence.*;

import com.angMetal.orders.enums.TypeDeTransaction;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionID;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "date_transaction", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_de_transaction", nullable = false)
    private TypeDeTransaction typeDeTransaction;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deponseID", nullable = false)
    private Deponse deponse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facture_id") // Adjust the column name as per your schema
    private FactureVente facture;

}