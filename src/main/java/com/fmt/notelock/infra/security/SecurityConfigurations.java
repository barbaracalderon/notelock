package com.fmt.notelock.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/cadastro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastro").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/cadastro/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/cadernos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cadernos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cadernos/{id}").hasAnyRole("ADMIN", "USUARIO")
                        .requestMatchers(HttpMethod.PUT, "/cadernos/{id}").hasAnyRole("ADMIN", "USUARIO")
                        .requestMatchers(HttpMethod.DELETE, "/cadernos/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/notas").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/notas").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/notas/{id}").hasAnyRole("ADMIN", "USUARIO")
                        .requestMatchers(HttpMethod.PUT, "/notas/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/notas/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
