package com.angMetal.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rapportID;

    private String typeRapport;

    @Temporal(TemporalType.DATE)
    private Date periodeDebut;

    @Temporal(TemporalType.DATE)
    private Date periodeFin;

    private Double Budget;

    // Many reports belong to one project
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projectID", nullable = false)
    private Projet projet;
}