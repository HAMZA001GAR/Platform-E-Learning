package com.jobintechtracking.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean completed;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String taskUrl;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Steps step;

    @ManyToOne
    @JoinColumn(name = "parcours_id")
    private Parcours parcours;

    public void startStep() {
        this.startTime = LocalDateTime.now();
        this.completed = false;
    }
    public void completeStep() {
        this.endTime = LocalDateTime.now();
        this.completed = true;
    }
}
