package com.espe.loanservice.dto;

public class RecoveryRequestDTO {
    private String email;
    private String recoveryCode;
    private String newPassword;

    public RecoveryRequestDTO() {
    }

    public RecoveryRequestDTO(String email, String recoveryCode, String newPassword) {
        this.email = email;
        this.recoveryCode = recoveryCode;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
