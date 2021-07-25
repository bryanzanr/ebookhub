package com.herokuapp.ebookhub.merchants.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Merchants implements Serializable {

    private String id;
    private String first_name;
    private String last_name;
    private String merchant_name;
    private String profile_picture;
    private String username;

    // public Merchants(String id) {
    //     this.id = id;
    // }

}