package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.UserDTO;
import com.jobintechtracking.app.entities.Users;

import java.util.List;

public interface UserService {

    List<Users> findAll();
    Users save(Users users);
    Users findUserById(Long id);
    void deleteUserById(Long id);
    List<UserDTO> getUsersByParcoursId(Long parcoursId);
}
