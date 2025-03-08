package com.example.peasinapod.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public JwtTokenUtil() {
        try {
            secret = new String(Files.readAllBytes(Paths.get("src/main/resources/SecretKey")));
            logger.info("Secret key loaded successfully");
        } catch (IOException e) {
            logger.error("Could not read secret key file", e);
            throw new RuntimeException("Could not read secret key file", e);
        }
    }

    public String generateToken(String email) {
        logger.info("Generating token for email: {}", email);
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        logger.info("Token generated: {}", token);
        return token;
    }

    public String getEmailFromToken(String token) {
        logger.info("Extracting email from token: {}", token);
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();
        logger.info("Email extracted: {}", email);
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        logger.info("Validating token: {}", token);
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            logger.info("Token is valid");
            return true;
        } catch (Exception e) {
            logger.error("Token validation failed", e);
            return false;
        }
    }
}