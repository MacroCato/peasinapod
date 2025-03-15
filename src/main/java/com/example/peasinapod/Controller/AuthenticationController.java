package com.example.peasinapod.Controller;

import com.example.peasinapod.Data.DTO.LoginRequest;
import com.example.peasinapod.Data.DTO.LoginResponse;
import com.example.peasinapod.Data.DTO.SignupRequest;
import com.example.peasinapod.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        logger.debug("Authentication Controller: Logging in user");
        return authenticationService.login(loginRequest, request);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest, HttpServletRequest request){
        logger.debug("Authentication Controller: Registering user");
        return authenticationService.registerUser(signupRequest, request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        logger.debug("Authentication Controller: Logging out user");
        return authenticationService.logout(request);
    }
}
