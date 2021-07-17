package com.herokuapp.ebookhub.user.dto.request;

public class RegisterRequest extends LoginRequest {
        
    private String email;

    private String role;

    public String getEmail() {
        return this.email;
    }

    public String getRole() {
        return this.role;
    }
    
}