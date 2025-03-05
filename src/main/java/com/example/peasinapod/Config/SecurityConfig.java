package com.example.peasinapod.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                                exceptionHandling
//                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                )
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers(HttpMethod.GET, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/likes").permitAll()
                                // .requestMatchers(HttpMethod.POST, "/api/likes/**").authenticated()
                                .anyRequest().authenticated()
                        // auth.requestMatchers(HttpMethod.GET, "/public_resource").permitAll()
                        //        .requestMatchers("/api/auth/**").permitAll()
                        //        .requestMatchers("/api/profiles").permitAll()
                        //        .anyRequest().authenticated()

                )
//                .logout(logout -> logout
//                        .logoutUrl("/api/auth/logout")
//                        .invalidateHttpSession(true) // Invalidate the session
//                        .clearAuthentication(true) // Clear authentication context
//                        .deleteCookies("JSESSIONID") // Delete JSESSIONID cookie
//                        .permitAll() // Allow access to the logout endpoint without authentication
//                )

                // Enable sessions;
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        System.out.println("in authenticationManager");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
