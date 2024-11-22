package com.angMetal.orders.entity;

import com.angMetal.orders.enums.StatutProjet;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters.")
    private String nomProjet;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Start date is required.")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private StatutProjet statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientID", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<TimeSheet> timeSheets;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Report> reports;
}