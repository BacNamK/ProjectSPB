package com.example.projectTLearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // 1
                .csrf(csrf -> csrf.disable())

                // 2
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(basic -> basic.disable())

                // 3
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()

                );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
