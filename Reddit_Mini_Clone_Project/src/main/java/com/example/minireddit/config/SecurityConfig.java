package com.example.minireddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/community/**", "/post/**").permitAll()
                        .requestMatchers("/api/votes/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/votes/**"));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
