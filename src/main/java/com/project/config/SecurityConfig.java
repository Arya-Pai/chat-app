package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	 http
         .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/login.jsp", "/api/loginController", "/api/registerController", "/registration.jsp").permitAll() // Allow unauthenticated access
             .anyRequest().authenticated()
         )
         .sessionManagement(session -> session
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         )
         .exceptionHandling(ex -> ex
             .authenticationEntryPoint((request, response, authException) ->
                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
         );


     return http.build();
 }

	
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
