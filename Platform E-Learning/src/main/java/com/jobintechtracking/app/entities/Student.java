package com.jobintechtracking.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobintechtracking.app.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Student extends Users  {
    @ManyToOne
    private Parcours parcours;

    @ManyToOne
    private Formations formations;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<StudentStep> studentSteps;

}