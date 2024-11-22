package com.angMetal.orders.entity;


import com.angMetal.orders.enums.TypeClient;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;

    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters.")
    private String nom;

    @Column
    private String adresse;

    @Column(unique = true)
    @Email(message = "Please provide a valid email.")
    private String email;

    @Column
    private String numeroTel;

    @Enumerated(EnumType.STRING)
    private TypeClient typeClient;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Devis> devis;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<FactureVente> factureVentes;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Projet> projets;
}
