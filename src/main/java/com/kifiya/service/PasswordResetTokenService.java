// src/main/java/com/kifiya/service/PasswordResetTokenService.java
package com.kifiya.service;

import com.kifiya.model.PasswordResetToken;
import com.kifiya.model.User;
import com.kifiya.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Value("${app.password-reset.expiration-minutes:60}")
    private int expirationMinutes;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Transactional
    public PasswordResetToken createPasswordResetTokenForUser(User user) {
        passwordResetTokenRepository.deleteByUser(user); // Delete existing tokens for this user
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(expirationMinutes);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(expiryDate);
        return passwordResetTokenRepository.save(resetToken);
    }

    public PasswordResetToken validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenRepository.findByToken(token);
        if (resetTokenOptional.isEmpty()) {
            throw new RuntimeException("Invalid password reset token.");
        }
        return resetTokenOptional.get();
    }

    @Transactional
    public void deleteToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }
}