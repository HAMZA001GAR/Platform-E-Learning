package com.jobintechtracking.app.auth;


import com.jobintechtracking.app.DTO.AuthenticationRequest;
import com.jobintechtracking.app.DTO.AuthenticationResponse;
import com.jobintechtracking.app.DTO.UserDTO;
import com.jobintechtracking.app.exceptions.EmailNotFoundException;
import com.jobintechtracking.app.entities.*;
import com.jobintechtracking.app.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final StudentRepository studentRepository;
    private final ExpertRepository expertRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ParcoursRepository parcoursRepository;
    private final FormationsRepository formationsRepository;


    public String register(AuthenticationRequest request) {

        if (request.getRoles ( ) == null) {
            return "Role is required";
        }

        switch (request.getRoles ( )) {
            case STUDENT -> {
                Student student = new Student ( );
                student.setFirstName ( request.getFirstName ( ) );
                student.setLastName ( request.getLastName ( ) );
                student.setEmail ( request.getEmail ( ) );
                student.setPassword ( passwordEncoder.encode ( request.getPassword ( ) ) );
                student.setRoles ( request.getRoles ( ) );

                if (request.getParcoursId ( ) > 0) {
                    Parcours parcours = parcoursRepository.findById ( request.getParcoursId ( ) )
                            .orElseThrow ( () -> new IllegalArgumentException ( "Invalid Parcours ID" ) );
                    student.setParcours ( parcours );
                    System.out.println ( "student.setParcours: OK" );
                }
                if (request.getFormationsId ( ) > 0) {
                    Formations formations = formationsRepository.findById ( request.getFormationsId ( ) ).orElseThrow ( () -> new IllegalArgumentException ( "Invalid Parcours ID" ) );
                    student.setFormations ( formations );
                }


                studentRepository.save ( student );
            }
            case EXPERT -> {
                Expert expert = new Expert ( );
                expert.setFirstName ( request.getFirstName ( ) );
                expert.setLastName ( request.getLastName ( ) );
                expert.setEmail ( request.getEmail ( ) );
                expert.setPassword ( passwordEncoder.encode ( request.getPassword ( ) ) );
                expert.setRoles ( request.getRoles ( ) );

                if (request.getParcoursId ( ) > 0) {
                    Parcours parcours = parcoursRepository.findById ( request.getParcoursId ( ) )
                            .orElseThrow ( () -> new IllegalArgumentException ( "Invalid Parcours ID" ) );
                    expert.setParcours ( parcours );
                }

                expertRepository.save ( expert );
            }
            case ADMIN -> {
                Admin admin = new Admin ( );
                admin.setFirstName ( request.getFirstName ( ) );
                admin.setLastName ( request.getLastName ( ) );
                admin.setEmail ( request.getEmail ( ) );
                admin.setPassword ( passwordEncoder.encode ( request.getPassword ( ) ) );
                admin.setRoles ( request.getRoles ( ) );

                adminRepository.save ( admin );
            }
            default -> {
                Users user = Users.builder ( )
                        .firstName ( request.getFirstName ( ) )
                        .lastName ( request.getLastName ( ) )
                        .email ( request.getEmail ( ) )
                        .password ( passwordEncoder.encode ( request.getPassword ( ) ) )
                        .roles ( request.getRoles ( ) )
                        .build ( );

                userRepository.save ( user );
            }
        }
        return "User registered successfully";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        if (request.getEmail ( ).isEmpty ( ) || request.getPassword ( ).isEmpty ( )) {
            throw new EmailNotFoundException ( "Fields are required" );
        }

        authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken (
                        request.getEmail ( ) ,
                        request.getPassword ( )
                )
        );


        var user = userRepository.findByEmail ( request.getEmail ( ) ).orElseThrow (
                () -> new EmailNotFoundException ( "Invalid email or password" )
        );

        var jwtToken = jwtService.generateToken ( user );

        UserDTO userDTO = mapToUserDTO ( user );

        return AuthenticationResponse.builder ( )
                .token ( jwtToken )
                .user ( userDTO )
                .build ( );
    }


    private UserDTO mapToUserDTO(Users user) {
        Long formationsId = null;
        if (user instanceof Student && ((Student) user).getFormations ( ) != null) {
            formationsId = ((Student) user).getFormations ( ).getId ( );
        }
        Long parcoursId = null;
        if (user instanceof Expert && ((Expert) user).getParcours ( ) != null) {
            parcoursId = ((Expert) user).getParcours ( ).getId ( );
        }
        return UserDTO.builder ( )
                .id ( user.getId ( ) )
                .firstName ( user.getFirstName ( ) )
                .lastName ( user.getLastName ( ) )
                .email ( user.getEmail ( ) )
                .roles ( user.getRoles ( ) )
                .formationsId ( formationsId )
                .parcoursId ( parcoursId )
                .build ( );
    }


}
