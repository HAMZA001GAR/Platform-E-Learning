package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.UserDTO;
import com.jobintechtracking.app.entities.Users;
import com.jobintechtracking.app.repositories.UserRepository;
import com.jobintechtracking.app.services.facade.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userrepository;

    public UserServiceImpl(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    @Override
    public List <Users> findAll() {
        return userrepository.findAll ( );
    }

    @Override
    @Transactional
    public Users save(Users users) {
        return userrepository.save ( users );
    }

    @Override
    public Users findUserById(Long id) {
        return userrepository.findById ( id ).orElse ( null );
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userrepository.deleteById ( id );
    }


    public List <UserDTO> getUsersByParcoursId(Long parcoursId) {
        List <Object[]> resultsSteps = userrepository.getUsersWithStepsByParcoursId ( parcoursId );
        List <Object[]> resultsUsers = userrepository.findUsersByParcoursId ( parcoursId );

        List <UserDTO> users = new ArrayList <> ( );

        for (Object[] row : resultsSteps) {
            Long userId = (Long) row[0];
            String firstName = (String) row[1];
            String lastName = (String) row[2];
            Long parcoursIdFromSteps = (Long) row[3];
            int stepsTaken = ((Long) row[4]).intValue ( );
            int totalSteps = ((Long) row[5]).intValue ( );
            String progress = (String) row[6];

            UserDTO userDTO = new UserDTO ( userId , firstName , lastName , parcoursIdFromSteps , stepsTaken , totalSteps , progress );
            users.add ( userDTO );
        }

        for (Object[] row : resultsUsers) {
            Long userId = (Long) row[0];

            boolean userAlreadyAdded = users.stream ( ).anyMatch ( userDTO -> userDTO.getId ( ).equals ( userId ) );
            if (!userAlreadyAdded) {
                String firstName = (String) row[1];
                String lastName = (String) row[2];
                UserDTO userDTO = new UserDTO ( userId , firstName , lastName , null , 0 , 0 , "0" );
                userDTO.setFirstName ( firstName );
                userDTO.setLastName ( lastName );
                users.add ( userDTO );
            }
        }
        return users;
    }

}