package com.jobintechtracking.app.entities;

import com.jobintechtracking.app.enums.StepProcess;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Steps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int durationInMinutes;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "parcours_id")
    private Parcours parcours;

    @Enumerated(EnumType.STRING)
    private StepProcess stepProcess;

}
