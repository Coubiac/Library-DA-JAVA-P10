package com.library.users.model.request;

import lombok.Data;

@Data
public class LoginRequestModel {

    private String email;
    private String password;
}
