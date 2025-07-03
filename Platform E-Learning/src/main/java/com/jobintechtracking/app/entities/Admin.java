package com.jobintechtracking.app.entities;


import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Admin extends Users {
    private long id;


}
