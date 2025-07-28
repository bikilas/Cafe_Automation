package com.kifiya.service;

import com.kifiya.dto.UserDto;
import com.kifiya.util.PasswordUtil;
import com.kifiya.model.User;
import com.kifiya.model.PasswordResetToken;
import com.kifiya.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kifiya.exception.DuplicateEmailException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       PasswordResetTokenService passwordResetTokenService,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @Transactional
    public User registerNewUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private String simpleHash(String password) {
        return PasswordUtil.simpleHash(password);
    }

    @Transactional
    public void updatePassword(String username, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found for password update.");
        }

        User user = userOptional.get();
        user.setPassword(simpleHash(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void initiatePasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            System.out.println("Password reset requested for non-existent email: " + email);
            return;
        }

        User user = userOptional.get();
        // This method call is now on the injected PasswordResetTokenService
        PasswordResetToken resetToken = passwordResetTokenService.createPasswordResetTokenForUser(user);
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken.getToken());
    }

    @Transactional
    public void resetPasswordWithToken(String token, String newPassword) {
        // This method call is now on the injected PasswordResetTokenService
        PasswordResetToken resetToken = passwordResetTokenService.validatePasswordResetToken(token);

        if (resetToken.isExpired()) {
            // This method call is now on the injected PasswordResetTokenService
            passwordResetTokenService.deleteToken(resetToken);
            throw new RuntimeException("Password reset token has expired.");
        }

        User user = resetToken.getUser();
        if (user == null) {
            throw new RuntimeException("User associated with token not found.");
        }

        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters long.");
        }

        user.setPassword(simpleHash(newPassword));
        userRepository.save(user);
        // This method call is now on the injected PasswordResetTokenService
        passwordResetTokenService.deleteToken(resetToken);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setRoles(userDto.getRoles());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(simpleHash(userDto.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDto(user);
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateEmailException("Email already registered.");
        }
        if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        
        // Generate username from email if not provided
        String username = (userDto.getUsername() != null && !userDto.getUsername().trim().isEmpty()) 
            ? userDto.getUsername() 
            : generateUsernameFromEmail(userDto.getEmail());
            
        if (userRepository.existsByEmail(username)) {
            throw new RuntimeException("Username is already taken.");
        }

        User newUser = new User();
        newUser.setName(userDto.getName().trim());
        newUser.setEmail(userDto.getEmail().trim().toLowerCase());
        newUser.setUsername(username);
        newUser.setPassword(simpleHash(userDto.getPassword()));
        
        // Set default role if none provided
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            newUser.setRoles(List.of("ROLE_CUSTOMER"));
        } else {
            newUser.setRoles(userDto.getRoles());
        }

        User savedUser = userRepository.save(newUser);
        logger.info("Created new user with ID: {}, email: {}, username: {}", 
            savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());
            
        return convertToDto(savedUser);
    }

    private String generateUsernameFromEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        // Extract the part before @ from email and add a random string to make it unique
        String username = email.split("@")[0].toLowerCase()
                .replaceAll("[^a-z0-9]", "") // Remove special characters
                .replaceAll("\\s+", "_"); // Replace spaces with underscores
        
        // Ensure username is not empty after cleaning
        if (username.isEmpty()) {
            username = "user";
        }
        
        // Add random string to make it unique
        return username + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    private UserDto convertToDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
    }
}