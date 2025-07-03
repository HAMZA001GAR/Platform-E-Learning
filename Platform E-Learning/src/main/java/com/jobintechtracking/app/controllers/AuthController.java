package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.AuthenticationRequest;
import com.jobintechtracking.app.exceptions.EmailNotFoundException;
import com.jobintechtracking.app.auth.AuthenticationService;
import com.jobintechtracking.app.services.facade.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity <Map <String, String>> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok ( Collections.singletonMap ( authenticationService.register ( request ) , "" ) );
    }


    @PostMapping("/authenticate")
    public ResponseEntity <Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok ( authenticationService.authenticate ( request ) );
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status ( HttpStatus.CONFLICT ).body ( e.getMessage ( ) );
        }
    }
}
