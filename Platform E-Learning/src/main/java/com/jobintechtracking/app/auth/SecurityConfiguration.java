package com.jobintechtracking.app.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthentificationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf ( AbstractHttpConfigurer :: disable )
                .authorizeHttpRequests ( authorize -> authorize
                        .requestMatchers ( "/api/auth/**" , "/parcours/**" , "/formations/all" , "/uploads/**" ).permitAll ( )
                        .anyRequest ( ).authenticated ( )
                )
                .sessionManagement ( session -> session
                        .sessionCreationPolicy ( SessionCreationPolicy.STATELESS ) // Stateless session management
                )
                .authenticationProvider ( authenticationProvider ) // Set custom authentication provider
                .addFilterBefore ( jwtAuthFilter , UsernamePasswordAuthenticationFilter.class ); // Add JWT filter

        return http.build ( );
    }
}
