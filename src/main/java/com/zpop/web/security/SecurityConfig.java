package com.zpop.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //TODO: csrf
            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers(HttpMethod.GET,"/").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/js/**").permitAll()
                            .requestMatchers("/images/**").permitAll()
                            .requestMatchers("/register/**").permitAll()
                            .requestMatchers("/search/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/meeting/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/comment/**").permitAll()
                            .requestMatchers("/login/**").permitAll()
                            .requestMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                            .anyRequest().hasRole("ROLE_USER"))
            .logout(logout ->
                        logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
