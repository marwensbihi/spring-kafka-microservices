package com.angMetal.orders.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fournisseurID;

    @Column(nullable = false)
    private String nom;

    private String adresse;

    @Column(unique = true)
    @Email(message = "Please provide a valid email.")
    private String email;

    private String numeroTel;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Deponse> deponses;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<FactureAchat> factureAchats;
}
