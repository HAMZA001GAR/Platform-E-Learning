package com.jobintechtracking.app.DTO;

import com.jobintechtracking.app.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Roles roles;
    private long parcoursId;

//    this is added by hamza
    private long formationsId;

}
