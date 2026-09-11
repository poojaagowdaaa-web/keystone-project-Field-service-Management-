package com.keystone.field_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.keystone.field_service.Entity.Role;
import com.keystone.field_service.Entity.User;
import com.keystone.field_service.Repository.RoleRepository;
import com.keystone.field_service.Repository.UserRepository;
import com.keystone.field_service.Security.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;


    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }

        // Find USER role from database
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new RuntimeException("USER role not found in database"));

        // Encrypt password
        user.setPasswordHash(
                passwordEncoder.encode(user.getPasswordHash())
        );

        // Set role
        user.setRole(userRole);

        // Activate user
        user.setActive(true);

        // Save user
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(),
                                    request.getPassword()
                            )
                    );

            String token = jwtUtil.generateToken(request.getEmail());

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            LoginResponse response = new LoginResponse(
                    token,
                    user.getEmail(),
                    user.getRole().getName()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(401)
                    .body("Login failed: " + e.getMessage());
        }
    }


    // =========================
    // LOGIN REQUEST
    // =========================

    public static class LoginRequest {

        private String email;
        private String password;

        public LoginRequest() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    // =========================
    // LOGIN RESPONSE
    // =========================

    public static class LoginResponse {

        private String token;
        private String email;
        private String role;

        public LoginResponse() {
        }

        public LoginResponse(String token, String email, String role) {
            this.token = token;
            this.email = email;
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}