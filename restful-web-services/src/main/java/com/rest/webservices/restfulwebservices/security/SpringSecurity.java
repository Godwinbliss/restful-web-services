package com.rest.webservices.restfulwebservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       // 1) All request are authenticated
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        // if a request is not authenticated, a web page is shown
        http.httpBasic(Customizer.withDefaults());

        //disable csrf -> POST, PUT
       // http.csrf().disable();
        return http.build();
    }
}
