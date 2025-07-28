package com.kifiya.service;

import com.kifiya.dto.LoginRequest;
import com.kifiya.util.PasswordUtil;
import com.kifiya.dto.LoginResponse;
import com.kifiya.model.User;
import com.kifiya.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            logger.error("Login request or credentials are null");
            throw new RuntimeException("Email and password are required");
        }
        
        logger.info("Attempting to authenticate user with email: {}", loginRequest.getEmail());
        
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", loginRequest.getEmail());
                    return new RuntimeException("Invalid email or password");
                });

        logger.info("User found. Comparing passwords...");
        logger.debug("Stored password hash: [{}]", user.getPassword());
        
        // Hash the provided password for comparison using the same method as UserService
        String hashedPassword = PasswordUtil.simpleHash(loginRequest.getPassword());
        logger.debug("Provided password hash: [{}]", hashedPassword);

        if (!user.getPassword().equals(hashedPassword)) {
            logger.error("Password mismatch for user: {}", loginRequest.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        logger.info("Authentication successful for user: {}", loginRequest.getEmail());
        return new LoginResponse(user.getId(), user.getEmail(), user.getName());
    }

    // Change return type to boolean
    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    // Add missing method
    public void initiatePasswordReset(String email) {
        // Implementation for password reset
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("If this email exists, a password reset link will be sent");
        }
        // In a real app, generate token and send email
    }
}
// @Service
// public class AuthService {

//     @Autowired
//     private UserRepository userRepository;

//     public LoginResponse authenticateUser(LoginRequest loginRequest) {
//         User user = userRepository.findByEmail(loginRequest.getEmail())
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         if (!user.getPassword().equals(loginRequest.getPassword())) {
//             throw new RuntimeException("Invalid credentials");
//         }

//         return new LoginResponse(
//         );
//     }

//     public User getCurrentUser() {
//         throw new RuntimeException("Not implemented");
//     }

//     public boolean changePassword(String username, String currentPassword, String newPassword) {
//         throw new UnsupportedOperationException("Not implemented");
//     }

//     public void forgotPassword(String email) {
//         throw new UnsupportedOperationException("Not implemented");
//     }

//     public void resetPassword(String token, String newPassword) {
//         throw new UnsupportedOperationException("Not implemented");
//     }
// }