package com.herokuapp.ebookhub.user.dto.response;

import java.util.List;
import com.herokuapp.ebookhub.user.entities.User;

public class LoginSuccessResponse {
    private List<User> users;

    public LoginSuccessResponse() {}
    public LoginSuccessResponse(List<User> users) {
        this.users = users;
    }
}