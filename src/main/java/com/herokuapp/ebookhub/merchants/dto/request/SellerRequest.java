package com.herokuapp.ebookhub.merchants.dto.request;

public class SellerRequest {

    private String firstName;

    private String lastName;

    private String merchantName;

    private String profilePicture;

    private String username;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public String getUsername() {
        return this.username;
    }

}