package com.trader.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.trader.auth.security.AdminUserDetailsService;
import com.trader.auth.security.PlayerAuthProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        DaoAuthenticationProvider adminAuthProvider,
                        PlayerAuthProvider playerAuthProvider) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**", "/.well-known/jwks.json",
                                                                "/api/internal/**")
                                                .permitAll()
                                                .requestMatchers("/actuator/health").permitAll()
                                                .anyRequest().authenticated())
                                .authenticationProvider(adminAuthProvider)
                                .authenticationProvider(playerAuthProvider)
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http,
                        DaoAuthenticationProvider adminAuthProvider,
                        PlayerAuthProvider playerAuthProvider) throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .authenticationProvider(adminAuthProvider)
                                .authenticationProvider(playerAuthProvider)
                                .build();
        }

        @Bean
        public DaoAuthenticationProvider adminAuthProvider(AdminUserDetailsService adminUserDetailsService,
                        PasswordEncoder passwordEncoder) {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(adminUserDetailsService);
                provider.setPasswordEncoder(passwordEncoder);
                return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
