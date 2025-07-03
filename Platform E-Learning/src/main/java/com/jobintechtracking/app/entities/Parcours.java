package com.jobintechtracking.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String parcoursName;
    private String parcoursDescription;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formations formations;

    @OneToMany(mappedBy = "parcours")
    private List<ParcoursFormation> parcoursFormations;

}
