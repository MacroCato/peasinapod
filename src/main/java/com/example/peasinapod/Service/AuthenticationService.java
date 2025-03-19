package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.Role;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Data.DTO.LoginRequest;
import com.example.peasinapod.Data.DTO.LoginResponse;
import com.example.peasinapod.Data.DTO.SignupRequest;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Repository.RoleRepository;
import com.example.peasinapod.Repository.UserRepository;
import com.example.peasinapod.Security.JwtTokenUtil;
import com.example.peasinapod.Service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

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

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, HttpServletRequest request) {
        logger.debug("Authentication Service: Logging in user");
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        logger.debug("Authentication Service: User authenticated");

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenUtil.generateToken(loginRequest.getEmail(), user.getId());
        logger.debug("Authentication Service: Token generated");
        LoginResponse loginResponse = new LoginResponse(token, user.getId());
        logger.debug("Authentication Service: Login response created");
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest, HttpServletRequest request) {
        logger.debug("Authentication Service: Registering user");
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            logger.debug("Authentication Service: Email already used");
            return new ResponseEntity<>("Email already used!", HttpStatus.BAD_REQUEST);
        }
        logger.debug("Authentication Service: Email not used. Creating new user");
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        // encode password and set it to user object
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        User savedUser = userRepository.save(user);
        logger.debug("Authentication Service: User created. Adding role");
        Role role = roleRepository.findByName("ROLE_USER");
        logger.debug("Authentication Service: Role found");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        savedUser.setRoles(roles);
        logger.debug("Authentication Service: Role added");
        userRepository.save(savedUser);

        logger.debug("Authentication Service: Role created. Creating profile");
        Profile profile = new Profile();
        profile.setFirstName(signupRequest.getFirstName());
        profile.setSurname(signupRequest.getSurname());
        // Set the nickname to be the First Name to start with. 
        // The user can change afterwards.
        profile.setNickname(signupRequest.getFirstName());
        profile.setUser(savedUser);

        profileRepository.save(profile);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        logger.debug("Authentication Service: Logging out user");
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer " prefix

        if (jwtTokenUtil.validateToken(token)) {
            logger.debug("Authentication Service: Token is valid. Adding token to blacklist");
            tokenBlacklistService.addTokenToBlacklist(token);
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        } else {
            logger.debug("Authentication Service: Invalid token");
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }
}