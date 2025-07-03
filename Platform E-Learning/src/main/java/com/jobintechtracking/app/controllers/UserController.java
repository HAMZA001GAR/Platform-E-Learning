package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.UserDTO;
import com.jobintechtracking.app.services.facade.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController ( UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/by-parcours/{parcoursId}")
    public ResponseEntity<List<UserDTO>> getUsersByParcoursId(@PathVariable Long parcoursId) {
        List<UserDTO> users = userService.getUsersByParcoursId(parcoursId);
        return ResponseEntity.ok(users);
    }
}
