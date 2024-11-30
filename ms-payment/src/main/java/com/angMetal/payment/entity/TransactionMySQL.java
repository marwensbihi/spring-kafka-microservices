package com.angMetal.payment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.Date;
import enums.PaymentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
@Document(indexName = "transaction_index")
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

    @Column(name = "facture_vente_id", nullable = false)
    private Long factureVenteId;

    @Column(name = "facture_achat_id", nullable = false)
    private Long factureAchatId;
}
