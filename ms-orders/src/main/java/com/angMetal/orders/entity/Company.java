package com.angMetal.orders.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Entity(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private String id;

    @Column(name = "name")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters.")
    private String name;

    @Column(name = "description")
    @Size(min = 12, max = 500, message = "The name must be between 12 and 500 characters.")
    private String description;

    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters.")
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Projet> projets;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<User> users;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Banque banque;
}
