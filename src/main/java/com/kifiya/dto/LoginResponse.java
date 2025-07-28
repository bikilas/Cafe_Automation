package com.kifiya.dto;

public class LoginResponse {
    private Long userId;
    private String email;
    private String fullName;
    // Add other user details as needed

    // Constructors, getters, and setters
    public LoginResponse() {}

    public LoginResponse(Long userId, String email, String fullName) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}








// package com.kifiya.dto;

// import lombok.Data;
// import java.util.List;

// @Data
// public class LoginResponse {
//     private String token;
//     private Long userId;
//     private String name;
//     private String email;
//     private List<String> roles;
    
//     public LoginResponse(String token, Long userId, String name, String email, List<String> roles) {
//         this.token = token;
//         this.userId = userId;
//         this.name = name;
//         this.email = email;
//         this.roles = roles;
//     }
// }