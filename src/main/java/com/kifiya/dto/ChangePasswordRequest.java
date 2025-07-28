package com.kifiya.dto;

public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}





















// package com.kifiya.dto;

// // import lombok.Data;
// // import javax.validation.constraints.NotBlank;
// // import javax.validation.constraints.Size;

// // @Data
// // public class ChangePasswordRequest {
// //     @NotBlank(message = "Current password is required")
// //     private String currentPassword;
    
// //     @NotBlank(message = "New password is required")
// //     @Size(min = 6, message = "Password must be at least 6 characters")
// //     private String newPassword;
    
// //     @NotBlank(message = "Confirm password is required")
// //     private String confirmNewPassword;
// // }package com.kifiya.dto;

// import lombok.Data;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;

// @Data
// public class ChangePasswordRequest {
//     @NotBlank(message = "Current password is required")
//     private String currentPassword;
    
//     @NotBlank(message = "New password is required")
//     @Size(min = 6, message = "Password must be at least 6 characters")
//     private String newPassword;
    
//     @NotBlank(message = "Confirm password is required")
//     private String confirmNewPassword;s