package com.jobintechtracking.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Learning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @ElementCollection
    private List<String> url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "steps_id", referencedColumnName = "id")
    private Steps steps;

}
