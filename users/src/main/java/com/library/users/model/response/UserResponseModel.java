package com.library.users.model.response;

import lombok.Data;

@Data
public class UserResponseModel {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;

}
