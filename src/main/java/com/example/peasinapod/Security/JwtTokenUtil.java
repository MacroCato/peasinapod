package com.example.peasinapod.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.peasinapod.Service.TokenBlacklistService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    public JwtTokenUtil() {
        try {
            secret = new String(Files.readAllBytes(Paths.get("src/main/resources/SecretKey")));
            logger.info("Secret key loaded successfully");
        } catch (IOException e) {
            logger.error("Could not read secret key file", e);
            throw new RuntimeException("Could not read secret key file", e);
        }
    }

    public String generateToken(String email, Long userId) {
        logger.info("Generating token for email: {}", email);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        String token = Jwts.builder()
                .setClaims(claims)
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

    public Long getUserIdFromToken(String token) {
        logger.info("Extracting user ID from token: {}", token);
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        Long userId = claims.get("userId", Long.class);
        logger.info("User ID extracted: {}", userId);
        return userId;
    }

    public boolean validateToken(String token) {
        logger.info("Validating token: {}", token);
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            logger.error("Token is blacklisted");
            return false;
        }
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