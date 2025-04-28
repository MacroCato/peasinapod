package com.example.peasinapod.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.peasinapod.Service.CustomUserDetailsService;
import com.example.peasinapod.Security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.info("JwtAuthenticationFilter: Authorization request received");
        String header = request.getHeader("Authorization");
        logger.info("JwtAuthenticationFilter: Header retrieved: {}", header);
        String token = null;
        String email = null;

        if (header != null && header.startsWith("Bearer ")) {
            logger.info("JwtAuthenticationFilter: Extracting data from header");
            token = header.substring(7);
            logger.info("JwtAuthenticationFilter: Extracted token: {}", token);
            email = jwtTokenUtil.getEmailFromToken(token);
            logger.info("JwtAuthenticationFilter: Extracted email: {}", email);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("JwtAuthenticationFilter: Getting user by email: {}", email);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            logger.info("JwtAuthenticationFilter: User details loaded: {}", userDetails);
            if (jwtTokenUtil.validateToken(token)) {
                logger.info("JwtAuthenticationFilter: Token is valid");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}