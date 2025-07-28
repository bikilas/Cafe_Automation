package com.kifiya.dto;

public class ResetPasswordRequest {
    private String token;
    private String newPassword;
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    // public String getConfirmPassword() {
    //     return confirmPassword;
    // }
    
    // public void setConfirmPassword(String confirmPassword) {
    //     this.confirmPassword = confirmPassword;
    // }
    }