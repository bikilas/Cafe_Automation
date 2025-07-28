package com.kifiya.controller;

import com.kifiya.dto.LoginRequest;
import com.kifiya.dto.LoginResponse;
import com.kifiya.exception.AuthenticationException;
import com.kifiya.service.AuthService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody(required = false) LoginRequest loginRequest, HttpServletRequest request) {
        logger.info("=== Login Request ===");
        logger.info("Request URL: {}", request.getRequestURL());
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request Headers: {}", request.getHeaderNames());
        logger.info("Content-Type: {}", request.getHeader("Content-Type"));
        logger.info("Method: {}", request.getMethod());
        logger.info("Request Body: {}", loginRequest);
        
        if (loginRequest == null) {
            logger.error("Login request body is null");
            return ResponseEntity.badRequest().body("{\"error\": \"Request body is required\"}");
        }
        
        logger.info("Validating login for email: {}", loginRequest.getEmail());
        
        // Basic validation
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            logger.error("Email is missing");
            return ResponseEntity.badRequest().body("{\"error\": \"Email is required\"}");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            logger.error("Password is missing for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("{\"error\": \"Password is required\"}");
        }
        
        try {
            LoginResponse loginResponse = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // @PostMapping("/login")
    // public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
    //     try {
    //         LoginResponse loginResponse = authService.authenticateUser(loginRequest);
    //         return ResponseEntity.ok(loginResponse);
    //     } catch (RuntimeException e) {
    //         throw new AuthenticationException(e.getMessage());
    //     }
    // }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestParam String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        try {
            boolean changed = authService.changePassword(email, currentPassword, newPassword);
            if (changed) {
                return ResponseEntity.ok("Password changed successfully");
            }
            return ResponseEntity.badRequest().body("Failed to change password");
        } catch (RuntimeException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            authService.initiatePasswordReset(email);
            return ResponseEntity.ok("If this email exists, a password reset link will be sent");
        } catch (RuntimeException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private AuthService authService;

//     // Login endpoint
//     @PostMapping("/login")
//     public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
//         try {
//             LoginResponse loginResponse = authService.authenticateUser(loginRequest);
//             return ResponseEntity.ok(loginResponse);
//         } catch (RuntimeException e) {
//             throw new AuthenticationException(e.getMessage());
//         }
//     }

//     // Change password
//     @PostMapping("/change-password")
//     public ResponseEntity<LoginRequest> changePassword(
//             @RequestParam String currentPassword,
//             @RequestParam String newPassword) {
//         throw new AuthenticationException("Not implemented");
//     }

//     // Forgot password
//     @PostMapping("/forgot-password")
//     public ResponseEntity<LoginRequest> forgotPassword(@RequestParam String email) {
//         throw new AuthenticationException("Not implemented");
//     }

//     // Reset password
//     @PostMapping("/reset-password")
//     public ResponseEntity<LoginRequest> resetPassword(
//             @RequestParam String token,
//             @RequestParam String newPassword) {
//         throw new AuthenticationException("Not implemented");
//     }
    
//     // Global exception handlers
//     @ExceptionHandler(AuthenticationException.class)
//     public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
//     }
    
//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<?> handleException(Exception ex) {
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
//     }