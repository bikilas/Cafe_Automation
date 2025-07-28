package com.kifiya.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    // Constructors
    public PasswordResetToken() {}

    public PasswordResetToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public PasswordResetToken validatePasswordResetToken(String token2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validatePasswordResetToken'");
    }

    public void deleteToken(PasswordResetToken resetToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteToken'");
    }

    public PasswordResetToken createPasswordResetTokenForUser(User user2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPasswordResetTokenForUser'");
    }
}