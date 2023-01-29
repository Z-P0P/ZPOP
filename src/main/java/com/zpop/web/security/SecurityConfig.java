package com.zpop.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //TODO: csrf
            .authorizeHttpRequests(authorize ->
                    authorize
                            // meeting
                            .requestMatchers(HttpMethod.POST, "/api/meeting/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PATCH, "/api/meeting/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/api/meeting/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/meeting/**").hasAnyRole("USER")
                            // comment
                            .requestMatchers(HttpMethod.POST, "/api/comment/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PATCH, "/api/comment/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/api/comment/**").hasAnyRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/comment/**").hasAnyRole("USER")
                            // member
                            .requestMatchers("/api/member/**").hasAnyRole("USER")
                            // auth
                            .requestMatchers("/api/logout").hasAnyRole("USER")
                            // report
                            .requestMatchers("/api/report/**").hasAnyRole("USER")
                            // notification 
                            .requestMatchers("/api/notification/**").hasAnyRole("USER")
                            // admin
                            .requestMatchers("/api/admin/auth/login").permitAll()
                            .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                            .anyRequest().permitAll()
            )
            .logout(logout ->
                        logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
            ).exceptionHandling().authenticationEntryPoint(new ZpopAuthenticationEntryPoint());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
