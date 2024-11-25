package com.angMetal.payment.entity;


import javax.persistence.*;

import com.angMetal.orders.entity.FactureAchat;
import com.angMetal.orders.entity.FactureVente;
import enums.PaymentType;
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
    private PaymentType typeDeTransaction;

    @Column(name = "facture_vente_id", nullable = true)
    private Long factureVenteId; // Stores ID of FactureVente

    @Column(name = "facture_achat_id", nullable = true)
    private Long factureAchatId; // Stores ID of FactureAchat

}