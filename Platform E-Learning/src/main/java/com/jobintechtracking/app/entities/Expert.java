package com.jobintechtracking.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Expert extends Users {
    @ManyToOne
    private Parcours parcours;

}
