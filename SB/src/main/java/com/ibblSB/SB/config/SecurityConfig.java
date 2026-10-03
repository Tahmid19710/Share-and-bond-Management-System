package com.ibblSB.SB.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;


@Configuration
public class SecurityConfig {


    // ===============================
    // USERS
    // ===============================


    @Bean
    public UserDetailsService userDetailsService() {


        UserDetails maker = User.builder()

                .username("maker")

                .password("{noop}maker123")

                .roles("MAKER")

                .build();


        UserDetails checker = User.builder()

                .username("checker")

                .password("{noop}checker123")

                .roles("CHECKER")

                .build();


        UserDetails approver = User.builder()

                .username("approver")

                .password("{noop}approver123")

                .roles("APPROVER")

                .build();


        return new InMemoryUserDetailsManager(

                maker,

                checker,

                approver

        );

    }


    // ===============================
    // SECURITY CONFIG
    // ===============================


    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {


        http


                .authorizeHttpRequests(auth -> auth


                        // Public pages

                        .requestMatchers(

                                "/",

                                "/login",

                                "/auth/**",

                                "/css/**",

                                "/js/**",

                                "/images/**"

                        )

                        .permitAll()


                        // Common view page

                        .requestMatchers(

                                "/shareholders/view/**"

                        )

                        .hasAnyRole(

                                "MAKER",

                                "CHECKER",

                                "APPROVER"

                        )


                        // common Dashboard

                        .requestMatchers(

                                "/dashboard"

                        ).authenticated()


                        // Shareholder List access

                        .requestMatchers(

                                "/shareholders",

                                "/shareholders/**"

                        )

                        .hasAnyRole(

                                "MAKER",

                                "CHECKER",

                                "APPROVER"

                        )


                        // Checker

                        .requestMatchers(

                                "/checker/**"

                        )

                        .hasRole("CHECKER")


                        // Approver

                        .requestMatchers(

                                "/approver/**"

                        )

                        .hasRole("APPROVER")


                        .anyRequest()

                        .authenticated()


                );


        return http

                .formLogin(form -> form


                        .loginPage("/login")


                        .successHandler(

                                successHandler()

                        )


                        .failureUrl(

                                "/login?error"

                        )


                        .permitAll()


                )


                .logout(logout -> logout


                        .logoutUrl("/logout")


                        .logoutSuccessUrl("/")


                        .invalidateHttpSession(true)


                        .clearAuthentication(true)


                        .deleteCookies("JSESSIONID")


                        .permitAll()


                )


                .build();


    }
    // ===============================
    // LOGIN SUCCESS HANDLER
    // ===============================

    @Bean
    public AuthenticationSuccessHandler successHandler() {


        return (request, response, authentication) -> {


            String expectedRole = request.getParameter("expectedRole");


            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


            String actualRole = authorities.iterator().next().getAuthority();


            // ===========================
            // ROLE CHECK
            // ===========================

            if (expectedRole != null) {


                String expectedAuthority = "ROLE_" + expectedRole.toUpperCase();


                if (!actualRole.equals(expectedAuthority)) {


                    response.sendRedirect("/login?error");

                    return;

                }

            }


            // ===========================
            // REDIRECT
            // ===========================


            switch (actualRole) {


                case "ROLE_MAKER":

                    response.sendRedirect("/dashboard?role=maker");

                    break;


                case "ROLE_CHECKER":

                    response.sendRedirect("/checker/dashboard");

                    break;


                case "ROLE_APPROVER":

                    response.sendRedirect("/approver/dashboard");

                    break;


                default:

                    response.sendRedirect("/");


            }


        };


    }
}