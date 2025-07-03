package com.jobintechtracking.app.DTO;

import com.jobintechtracking.app.enums.Roles;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;
    private Long parcoursId;
    private String firstName;
    private String lastName;
    private int stepsTaken;
    private int totalSteps;
    private String progress;
    private String email;
    private Roles roles;

//    this is added by hamza to return as well the formations by the candidat
    private Long formationsId;

    public UserDTO(Long id,String firstName,String lastName, Long parcoursId, int stepsTaken, int totalSteps, String progress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.parcoursId = parcoursId;
        this.stepsTaken = stepsTaken;
        this.totalSteps = totalSteps;
        this.progress = progress;
    }

}