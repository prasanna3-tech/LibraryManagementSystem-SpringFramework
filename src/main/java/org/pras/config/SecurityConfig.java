package org.pras.config;

import org.pras.security.JwtAuthenticationFilter;
import org.pras.security.LmsUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                // =========================
                // PUBLIC ENDPOINTS
                // =========================

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/students"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/books",
                                "/books/*"
                        ).permitAll()

                        // =========================
                        // BOOKS
                        // =========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/books/add"
                        ).hasAnyRole("LIBRARIAN", "ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/books/*"
                        ).hasAnyRole("LIBRARIAN", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/books/*"
                        ).hasAnyRole("LIBRARIAN", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/students/*"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/students"
                        ).hasAnyRole("ADMIN", "LIBRARIAN")

                        // These will later become
                        // "that student / ADMIN"
                        .requestMatchers(
                                HttpMethod.GET,
                                "/students/*"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/students/*/reservation-notifications"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/students/*"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/students/*/password"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/borrow-records/issue"
                        ).hasRole("LIBRARIAN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/borrow-records/return"
                        ).hasRole("LIBRARIAN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/borrow-records/renew"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/borrow-records/reserve"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/borrow-records/pay-fine"
                        ).hasRole("LIBRARIAN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/borrow-records/fine/*"
                        ).hasAnyRole(
                                "STUDENT",
                                "LIBRARIAN",
                                "ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/borrow-records/student/*/borrowed-books",
                                "/borrow-records/student/*/due-dates",
                                "/borrow-records/student/*/overdue-books",
                                "/borrow-records/student/*/history"
                        ).hasAnyRole(
                                "STUDENT",
                                "LIBRARIAN",
                                "ADMIN"
                        )

                        // =========================
                        // LIBRARIANS
                        // =========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/librarians"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/librarians/*"
                        ).hasRole("ADMIN")

                        // Later: That librarian / ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/librarians/*"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/librarians"
                        ).hasRole("ADMIN")

                        // =========================
                        // ADMINS
                        // =========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/admins"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/admins/*"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/admins/system-settings"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/admins"
                        ).hasRole("ADMIN")

                        // =========================
                        // REPORTS
                        // =========================

                        .requestMatchers(
                                "/reports/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN"
                        )


                        // =========================
                        // EVERYTHING ELSE
                        // =========================

                        .anyRequest().authenticated()
                )

                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(
            LmsUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationProvider authenticationProvider) {

        return new ProviderManager(authenticationProvider);
    }
}