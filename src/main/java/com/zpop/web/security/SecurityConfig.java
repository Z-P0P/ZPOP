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
                            // meeting
                            .requestMatchers(HttpMethod.POST, "/meeting/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PATCH, "/meeting/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/meeting/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/meeting/**").hasAnyRole("USER")
                            // comment
                            .requestMatchers(HttpMethod.POST, "/comment/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PATCH, "/comment/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/comment/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/comment/**").hasAnyRole("USER")
                            // member
                            .requestMatchers("/member/**").hasAnyRole("USER")
                            // auth
                            .requestMatchers("/logout").hasAnyRole("USER")
                            // report
                            .requestMatchers("/report/**").hasAnyRole("USER")
                            // notification 
                            .requestMatchers("/notification/**").hasAnyRole("USER")
                            // admin
                         //   .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                            .anyRequest().permitAll()
            )
            .logout(logout ->
                        logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
