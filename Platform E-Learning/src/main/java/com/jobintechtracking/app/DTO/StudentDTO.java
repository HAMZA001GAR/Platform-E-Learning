package com.jobintechtracking.app.DTO;

import java.util.List;

public class StudentDTO {
    private Long id;

    private Long parcoursId;
    private List<Long> studentStepIds;

    private String FirstName;
    private String LastName;
    private String Email;
    private String Role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public Long getParcoursId() {
        return parcoursId;
    }

    public void setParcoursId(Long parcoursId) {
        this.parcoursId = parcoursId;
    }

    public List<Long> getStudentStepIds() {
        return studentStepIds;
    }

    public void setStudentStepIds(List<Long> studentStepIds) {
        this.studentStepIds = studentStepIds;
    }

}
