package com.example.peasinapod.Controller;

import com.example.peasinapod.Data.DTO.LoginRequest;
import com.example.peasinapod.Data.DTO.LoginResponse;
import com.example.peasinapod.Data.DTO.SignupRequest;
import com.example.peasinapod.Data.Common.Role;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Repository.RoleRepository;
import com.example.peasinapod.Repository.UserRepository;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Security.JwtTokenUtil;
import com.example.peasinapod.Service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        logger.debug("Authentication Controller: Logging in user");
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        logger.debug("Authentication Controller: User authenticated");

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenUtil.generateToken(loginRequest.getEmail(), user.getId());
        logger.debug("Authentication Controller: Token generated");
        LoginResponse loginResponse = new LoginResponse(token, user.getId());
        logger.debug("Authentication Controller: Returning token");
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest, HttpServletRequest request){
        logger.debug("Authentication Controller: Registering user");
        // add check for email exists in DB
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            logger.debug("Authentication Controller: Email already used");
            return new ResponseEntity<>("Email already used!", HttpStatus.BAD_REQUEST);
        }
        logger.debug("Authentication Controller: Email not used. Creating user");
        // create user object
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        User savedUser = userRepository.save(user);
        logger.debug("Authentication Controller: User created. Adding role");
        // add role to user
        Role role = roleRepository.findByName("ROLE_USER"); 
        logger.debug("Authentication Controller: Role found");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        savedUser.setRoles(roles);
        logger.debug("Authentication Controller: Role added");
        userRepository.save(savedUser);

        logger.debug("Authentication Controller: Role created. Creating profile");
        //Create basic profile
        Profile profile = new Profile();
        profile.setFirstName(signupRequest.getFirstName());
        profile.setSurname(signupRequest.getSurname());
        // Set the nickname to be the First Name to start with. The user can change afterwards.
        profile.setNickname(signupRequest.getFirstName());

        profileRepository.save(profile);   
        logger.debug("Authentication Controller: Profile created");

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        logger.debug("Authentication Controller: Logging out user");
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer " prefix

        if (jwtTokenUtil.validateToken(token)) {
            logger.debug("Authentication Controller: Token is valid");
            tokenBlacklistService.addTokenToBlacklist(token);
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        } else {
            logger.debug("Authentication Controller: Token is invalid");
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        
    }
}
