package com.angMetal.orders.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heuresTravaillees;

    @Temporal(TemporalType.DATE)
    private Date date;

    // Many timesheets belong to one project
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projectID", nullable = false)
    private Projet projet;
}
