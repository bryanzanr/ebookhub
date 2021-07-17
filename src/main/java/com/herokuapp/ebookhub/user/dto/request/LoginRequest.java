package com.herokuapp.ebookhub.user.dto.request;

// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.NoArgsConstructor;
// import lombok.Value;

// @Builder
// @AllArgsConstructor
// @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
// @Value
public class LoginRequest {

    private String username;

    private String password;
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}